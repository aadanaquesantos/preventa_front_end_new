package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.AvanceProveedorListAdapter;
import com.digital.inka.preventa.adapter.SpinnerPeriodoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.AvanceProveedor;
import com.digital.inka.preventa.model.AvanceProveedorListResponse;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.Periodo;
import com.digital.inka.preventa.model.PeriodoListResponse;
import com.digital.inka.preventa.model.StatusResponse;
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
 * Use the {@link AvanceProveedorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvanceProveedorFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private AvanceProveedorListAdapter mAdapter;
    private TextView tvTitle;
    private AppCompatSpinner material_spinner;
    private MaterialButton ivClose;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
     public AvanceProveedorFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AvanceProveedorFragment newInstance(String param1, String param2) {
        AvanceProveedorFragment fragment = new AvanceProveedorFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_avance_proveedor, container, false);
        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        ivClose=view.findViewById(R.id.ivClose);
        material_spinner=view.findViewById(R.id.material_spinner);
        tvTitle.setText("Avance por proveedor");
        recyclerView = (RecyclerView) view.findViewById(R.id.rvDirecciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      //  recyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().onBackPressed();

            }
        });
        callGetPeriodos();
        material_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Periodo periodo = (Periodo) material_spinner.getSelectedItem();
                callAvanceProveedorByPeriodo("DIAZPJOS",periodo.getAnnio(),periodo.getDescription());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
       return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    private void callGetPeriodos() {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Call<PeriodoListResponse> loginCall = ApiRetrofitShort.getInstance(BASE_URL).getUserService().getPeriodos();
        loginCall.enqueue(new Callback<PeriodoListResponse>() {
            @Override
            public void onResponse(Call<PeriodoListResponse> call, Response<PeriodoListResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    List<Periodo> periodos = response.body().getPeriodos();
                    SpinnerPeriodoAdapter spinnerPeriodoAdapter=new SpinnerPeriodoAdapter(getActivity(),R.layout.list_periodo_spinner_item,periodos);
                    material_spinner.setAdapter(spinnerPeriodoAdapter);
                    callAvanceProveedorByPeriodo("DIAZPJOS", spinnerPeriodoAdapter.getItem(0).getAnnio(), spinnerPeriodoAdapter.getItem(0).getDescription());

                    // ((ContenedorActivity) getActivity()).showProgress(false);
                 } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<PeriodoListResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
                ToastSilicon.toastDangerOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
            }
        });


    }


    private void callAvanceProveedorByPeriodo(String usuario, String annio, String periodo) {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario", usuario);
        dataConsulta.put("annio", annio);
        dataConsulta.put("periodo", periodo);
        Call<AvanceProveedorListResponse> avanceReponseCall = ApiRetrofitShort.getInstance(BASE_URL).getUserService().getAvanceProveedorByPeriodo(dataConsulta);
        avanceReponseCall.enqueue(new Callback<AvanceProveedorListResponse>() {
            @Override
            public void onResponse(Call<AvanceProveedorListResponse> call, Response<AvanceProveedorListResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    List<AvanceProveedor> items = response.body().getAvances();
                    //set data and list adapter
                    mAdapter = new AvanceProveedorListAdapter(getContext(), items);
                    recyclerView.setAdapter(mAdapter);

                    ((ContenedorActivity) getActivity()).showProgress(false);//verificar posible null

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);




                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<AvanceProveedorListResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
                ToastSilicon.toastDangerOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }


}