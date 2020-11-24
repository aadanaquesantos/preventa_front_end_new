package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.CarritoAdapter;
import com.digital.inka.preventa.adapter.ComisionesListAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Carrito;
import com.digital.inka.preventa.model.CarritoRequest;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.PedidoResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.util.SessionUsuario;
import com.google.android.material.button.MaterialButton;
import com.rabbil.toastsiliconlibrary.ToastSilicon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends DialogFragment  implements DialogAddCarritoFragment.CallbackAddCarrito {
    private RecyclerView recyclerView;
    private ComisionesListAdapter mAdapter;
    private TextView tvTitle;
    private AppCompatSpinner material_spinner;
    private MaterialButton ivClose;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
    private static CarritoRequest carritoRequestStatic;
    private MaterialButton btnArticulos;
    private MaterialButton btnPrevisualizar;
    private RecyclerView rvCarrito;
    private CarritoAdapter carritoAdapter;
    private DialogAddCarritoFragment.CallbackAddCarrito callbackAddCarrito;
    private TextView tvTotal;


    public CarritoFragment() {
        // Required empty public constructor
    }



    // TODO: Rename and change types and number of parameters
    public static CarritoFragment newInstance(CarritoRequest carritoRequest) {
        CarritoFragment fragment = new CarritoFragment();
        carritoRequestStatic=carritoRequest;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    SessionUsuario sessionUsuario;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         sessionUsuario=new SessionUsuario(getContext());

        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_carrito, container, false);
        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        ivClose=view.findViewById(R.id.ivClose);
        btnArticulos=view.findViewById(R.id.btnEdit);
        btnPrevisualizar=view.findViewById(R.id.btnProcesar);
         tvTitle.setText("Carrito de compras");
         tvTotal=view.findViewById(R.id.tvTotal);
         rvCarrito=view.findViewById(R.id.rvCarrito);
         callbackAddCarrito=this;
//         DialogAddCarritoFragment.newInstance(carritoRequestStatic);
//        DialogAddCarritoFragment dialogAddCarritoFragment = new DialogAddCarritoFragment();
//        dialogAddCarritoFragment.setCallbackAddCarrito(callbackAddCarrito);
//        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            ft.remove(prev);
//        }
//        ft.addToBackStack(null);


            if(sessionUsuario.getCarritoList()==null){
               // dialogAddCarritoFragment.show(ft, "dialog");
                ((ContenedorActivity) getActivity()).loadAddCarritoFragment(carritoRequestStatic,callbackAddCarrito);
            }else{
                if(sessionUsuario.getCarritoList().getListCarrito().isEmpty()){
                 //   dialogAddCarritoFragment.show(ft, "dialog");
                    ((ContenedorActivity) getActivity()).loadAddCarritoFragment(carritoRequestStatic,callbackAddCarrito);
                }else{
                    rvCarrito.setHasFixedSize(true);
                    rvCarrito.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

                    carritoAdapter=new CarritoAdapter(getContext(),sessionUsuario.getCarritoList().getListCarrito(),getActivity(),tvTotal);
                    rvCarrito.setAdapter(carritoAdapter);
                    tvTotal.setText("S/."+carritoAdapter.getTotal());
                }

            }





        btnArticulos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // DialogAddCarritoFragment.newInstance(carritoRequestStatic);
//                DialogAddCarritoFragment dialogAddCarritoFragment = new DialogAddCarritoFragment();
//                dialogAddCarritoFragment.setCallbackAddCarrito(callbackAddCarrito);
//                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                Fragment prev = getChildFragmentManager().findFragmentByTag("dialog");
//                if (prev != null) {
//                    ft.remove(prev);
//                }
//                ft.addToBackStack(null);
//                dialogAddCarritoFragment.show(ft, "dialog");
                ((ContenedorActivity) getActivity()).loadAddCarritoFragment(carritoRequestStatic,callbackAddCarrito);
            }
        });

        btnPrevisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carritoRequestStatic.setCarritoList(sessionUsuario.getCarritoList().getListCarrito());

               saveCarrito();
             //   System.out.println(carritoRequestStatic);
            }
        });


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dismiss();
                getActivity().onBackPressed();

            }
        });


       return view;
    }

    private void saveCarrito(){
        Call<PedidoResponse> callPedidoResponse = ApiRetrofitShort.getInstance(BASE_URL).getPedidoService().saveCarrito(carritoRequestStatic);
        callPedidoResponse.enqueue(new Callback<PedidoResponse>() {
            @Override
            public void onResponse(Call<PedidoResponse> call, Response<PedidoResponse> response) {
                PedidoResponse pedidoResponse=response.body();
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);

                    ((ContenedorActivity) getActivity()).loadPrevisualizarFragment(pedidoResponse.getPedido());

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);


                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);

                }


            }

            @Override
            public void onFailure(Call<PedidoResponse> call, Throwable t) {


            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public void updateRecycler(Carrito carrito) {

        rvCarrito.setHasFixedSize(true);
        rvCarrito.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        carritoAdapter=new CarritoAdapter(getContext(),sessionUsuario.getCarritoList().getListCarrito(),getActivity(),tvTotal);
        rvCarrito.setAdapter(carritoAdapter);
       tvTotal.setText("S/."+carritoAdapter.getTotal());

    }

    public interface CallbackUpdateRecycler {
        void updateRecycler(Carrito carrito);

    }
}