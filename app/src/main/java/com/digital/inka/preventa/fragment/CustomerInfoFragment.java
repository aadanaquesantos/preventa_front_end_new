package com.digital.inka.preventa.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.DireccionListAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.Customer;
import com.digital.inka.preventa.model.CustomerInfoResponse;
 import com.digital.inka.preventa.model.DispatchAddress;
import com.digital.inka.preventa.model.StatusResponse;
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
 * Use the {@link CustomerInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvTitle;
    private MaterialButton ivClose;
    private TextView tvDni;
    private TextView tvRuc;
    private TextView tvFijo;
    private TextView tvCelular;
    private TextView tvDescCliente;
    private TextView tvDirFacturacion;
    private TextView tvEmail;


    private RecyclerView rvDirecciones;
    private DireccionListAdapter mAdapter;
    // TODO: Rename and change types of parameters


    public CustomerInfoFragment() {
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
    public static CustomerInfoFragment newInstance(String param1, String param2) {
        CustomerInfoFragment fragment = new CustomerInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
private String codCliente;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        codCliente=getArguments().getString("codCliente");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_customer_info, container, false);

        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        tvTitle.setText("Datos de cliente");
        ivClose=view.findViewById(R.id.ivClose);
        tvDni=view.findViewById(R.id.tvDni);
        tvRuc=view.findViewById(R.id.tvRuc);
        tvFijo=view.findViewById(R.id.tvFijo);
        tvCelular=view.findViewById(R.id.tvCelular);
        tvDescCliente=view.findViewById(R.id.tvDescCliente);
        tvDirFacturacion=view.findViewById(R.id.tvDirFacturacion);
        rvDirecciones=view.findViewById(R.id.rvDirecciones);
        tvEmail=view.findViewById(R.id.tvEmail);
        rvDirecciones.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDirecciones.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        rvDirecciones.setHasFixedSize(true);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });
        callCustomerInfo(codCliente);
        return view;
    }

    private void callCustomerInfo(String codCliente) {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("codCliente", codCliente);
        dataConsulta.put("usuario", "DIAZPJOS");
         Call<CustomerInfoResponse> customerResponseCall = ApiRetrofitShort.getInstance(BASE_URL).getCustomerService().getCustomerInfo(dataConsulta);
        customerResponseCall.enqueue(new Callback<CustomerInfoResponse>() {
            @Override
            public void onResponse(Call<CustomerInfoResponse> call, Response<CustomerInfoResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    Customer customer=response.body().getCustomer();
                    tvDescCliente.setText(customer.getDescription());
                    tvDni.setText(customer.getDni()==null?"-":customer.getDni());
                    tvRuc.setText(customer.getRuc()==null?"-":customer.getRuc());
                    tvFijo.setText(customer.getPhone()==null?"-":customer.getPhone());
                    tvDirFacturacion.setText(customer.getAddress()==null?"-":customer.getAddress());
                    tvEmail.setText(customer.getEmail()==null?"-":customer.getEmail());
                    tvCelular.setText(customer.getCellPhone()==null?"-":customer.getCellPhone());


                    List<DispatchAddress> direcciones = response.body().getAddresses();
                    //set data and list adapter
                    mAdapter = new DireccionListAdapter(getContext(), direcciones);
                    rvDirecciones.setAdapter(mAdapter);

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
            public void onFailure(Call<CustomerInfoResponse> call, Throwable t) {
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