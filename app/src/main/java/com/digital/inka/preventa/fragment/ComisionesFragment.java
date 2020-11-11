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
import com.digital.inka.preventa.adapter.ComisionesListAdapter;
import com.digital.inka.preventa.adapter.SpinnerPeriodoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.AvancePolitica;
import com.digital.inka.preventa.model.AvancePoliticaListResponse;
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
 * Use the {@link ComisionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComisionesFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private ComisionesListAdapter mAdapter;
    private TextView tvTitle;
    private AppCompatSpinner material_spinner;
    private MaterialButton ivClose;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
     public ComisionesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ComisionesFragment newInstance(String param1, String param2) {
        ComisionesFragment fragment = new ComisionesFragment();

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
       View view= inflater.inflate(R.layout.fragment_avance_comisiones, container, false);
        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        ivClose=view.findViewById(R.id.ivClose);
        material_spinner=view.findViewById(R.id.material_spinner);
        tvTitle.setText("Comisiones");
        recyclerView = (RecyclerView) view.findViewById(R.id.rvDirecciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                callComisionesByPeriodo("DIAZPJOS",periodo.getAnnio(),periodo.getDescription());
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
                    callComisionesByPeriodo("DIAZPJOS", spinnerPeriodoAdapter.getItem(0).getAnnio(), spinnerPeriodoAdapter.getItem(0).getDescription());
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


    private void callComisionesByPeriodo(String usuario, String annio, String periodo) {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario", usuario);
        dataConsulta.put("annio", annio);
        dataConsulta.put("periodo", periodo);
        Call<AvancePoliticaListResponse> avanceReponseCall = ApiRetrofitShort.getInstance(BASE_URL).getUserService().getComisiones(dataConsulta);
        avanceReponseCall.enqueue(new Callback<AvancePoliticaListResponse>() {
            @Override
            public void onResponse(Call<AvancePoliticaListResponse> call, Response<AvancePoliticaListResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    List<AvancePolitica> items = response.body().getAvances();
                    //set data and list adapter
                    mAdapter = new ComisionesListAdapter(getContext(), items);
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
            public void onFailure(Call<AvancePoliticaListResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
                ToastSilicon.toastDangerOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }


}