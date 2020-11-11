package com.digital.inka.preventa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.DireccionListAdapter;
import com.digital.inka.preventa.adapter.SpinneCondicionAdapter;
import com.digital.inka.preventa.adapter.SpinneTipoDocsAdapter;
import com.digital.inka.preventa.adapter.SpinnerAlmacenAdapter;
import com.digital.inka.preventa.adapter.SpinnerPeriodoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Almacen;
import com.digital.inka.preventa.model.CondicionPago;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.Customer;
import com.digital.inka.preventa.model.CustomerPedidoResponse;
import com.digital.inka.preventa.model.DispatchAddress;
import com.digital.inka.preventa.model.DispatchAddressResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.TipoDoc;
import com.digital.inka.preventa.widget.LineItemDecoration;
import com.google.android.material.button.MaterialButton;
import com.rabbil.toastsiliconlibrary.ToastSilicon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DatosPedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatosPedidoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvTitle;
    private MaterialButton ivClose;
    private TextView tvDescCliente;
   private TextView tvDirFacturacion;
   private TextView tvDirDespacho;
    private TextView tvRuta;
    private TextView tvListaPrecios;
    private Spinner spinnerAlmacenes;
    private Spinner spinnerCondiciones;
    private Spinner spinnerTipoDocs;
    private RecyclerView rvDirecciones;
    private DireccionListAdapter mAdapter;
    private MaterialButton btnRealizarPedido;
    // TODO: Rename and change types of parameters


    public DatosPedidoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DatosPedidoFragment newInstance(String param1, String param2) {
        DatosPedidoFragment fragment = new DatosPedidoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
private String codCliente;
    private String codLocal;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        codCliente=getArguments().getString("codCliente");
        codLocal=getArguments().getString("codLocal");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_datos_pedido, container, false);

        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        tvTitle.setText("Datos del Pedido");
        ivClose=view.findViewById(R.id.ivClose);
        tvDescCliente=view.findViewById(R.id.tvDescCliente);
        tvDirFacturacion=view.findViewById(R.id.tvDirFacturacion);
        tvDirDespacho=view.findViewById(R.id.tvDirDespacho);
        tvRuta=view.findViewById(R.id.tvRuta);
        tvListaPrecios=view.findViewById(R.id.tvListaPrecios);
        spinnerAlmacenes=view.findViewById(R.id.spinnerAlmacenes);
        spinnerCondiciones=view.findViewById(R.id.spinnerCondPago);
        spinnerTipoDocs=view.findViewById(R.id.spinnerTipoDoc);
        btnRealizarPedido=view.findViewById(R.id.btnRealizarPedido);

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnRealizarPedido.setEnabled(false);
                btnRealizarPedido.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnRealizarPedido.setEnabled(true);
                        ((ContenedorActivity) getActivity()).loadCarritoFragment();
                    }
                },150); //150 is in milliseconds
            }
        });

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });
        callDatosClientePedido();
        return view;
    }

    private void callDatosClientePedido() {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("codCliente", codCliente);
        dataConsulta.put("codLocal", codLocal);
        dataConsulta.put("usuario", "DIAZPJOS");
         Call<CustomerPedidoResponse> customerPedidoResponse = ApiRetrofitShort.getInstance(BASE_URL).getCustomerService().getCustomerPedido(dataConsulta);
        customerPedidoResponse.enqueue(new Callback<CustomerPedidoResponse>() {
            @Override
            public void onResponse(Call<CustomerPedidoResponse> call, Response<CustomerPedidoResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    Customer customer=response.body().getCustomer();
                    DispatchAddress dispatchAddress=response.body().getDispatchAddress();
                    List<Almacen> almacenes=response.body().getAlmacenes();
                    List<CondicionPago> condiciones=response.body().getCondiciones();
                    List<TipoDoc> tipoDocs=response.body().getTipoDocs();
                  tvDescCliente.setText(customer.getDescription());
                  tvDirFacturacion.setText(customer.getAddress());
                    tvDirDespacho.setText(dispatchAddress.getDescription());
                    tvRuta.setText(dispatchAddress.getRoute().getCode()+"-"+dispatchAddress.getRoute().getDescription());
                    tvListaPrecios.setText(dispatchAddress.getListaPrecios().getCode()+"-"+dispatchAddress.getListaPrecios().getDescription());

                    SpinnerAlmacenAdapter spinnerAlmacenAdapter=new SpinnerAlmacenAdapter(getActivity(),R.id.tvDescAlmacen,almacenes);
                    spinnerAlmacenes.setAdapter(spinnerAlmacenAdapter);

                    SpinneCondicionAdapter spinneCondicionAdapter=new SpinneCondicionAdapter(getActivity(),R.id.tvDescCondicion,condiciones);
                    spinnerCondiciones.setAdapter(spinneCondicionAdapter);

                    SpinneTipoDocsAdapter spinneTipoDocsAdapter=new SpinneTipoDocsAdapter(getActivity(),R.id.tvDescTipoDoc,tipoDocs);
                    spinnerTipoDocs.setAdapter(spinneTipoDocsAdapter);
                    ((ContenedorActivity) getActivity()).showProgress(false);

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<CustomerPedidoResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
                ToastSilicon.toastDangerOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

//asociat un menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
       inflater.inflate(R.menu.menu_tab, menu);
    }
}