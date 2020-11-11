package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.SpinnerPeriodoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.AvanceResponse;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.Periodo;
import com.digital.inka.preventa.model.PeriodoListResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.util.UtilAndroid;
import com.google.android.material.button.MaterialButton;
import com.rabbil.toastsiliconlibrary.ToastSilicon;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvanceFragment extends DialogFragment {

    private TextView tvTitle;
    private AppCompatSpinner material_spinner;
    private TextView tvCountAvanceDias;
    private TextView tvTotalDias;
    private TextView tvRestoDias;
    private TextView tvLinealidad;
    private TextView tvNecesidad;
    private TextView tvAtendidos;
    private TextView tvProyeccion;
    private ProgressBar pbAvanceDia;
    private MaterialButton ivClose;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
     public AvanceFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AvanceFragment newInstance(String param1, String param2) {
        AvanceFragment fragment = new AvanceFragment();

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
       View view= inflater.inflate(R.layout.fragment_avance, container, false);
        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        tvTitle.setText("Avance de Ventas");
       tvCountAvanceDias=view.findViewById(R.id.tvCountAvanceDias);
       tvRestoDias=view.findViewById(R.id.tvCountRestoDias);
       tvTotalDias=view.findViewById(R.id.tvCountTotalDias);
       tvLinealidad=view.findViewById(R.id.tvLinealidad);
       tvNecesidad=view.findViewById(R.id.tvNecesidad);
       tvAtendidos=view.findViewById(R.id.tvAtendidos);
       tvProyeccion=view.findViewById(R.id.tvProyeccion);
        material_spinner=view.findViewById(R.id.material_spinner);
        pbAvanceDia=view.findViewById(R.id.pbAvanceDia);
        ivClose=view.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ivClose.setEnabled(false);
                 getActivity().onBackPressed();

            }
        });
        material_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Periodo periodo = (Periodo) material_spinner.getSelectedItem();

              callAvanceByPeriodo("DIAZPJOS",periodo.getAnnio(),periodo.getDescription());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        callGetPeriodos();
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
                    if (getActivity()!=null){
                        SpinnerPeriodoAdapter spinnerPeriodoAdapter=new SpinnerPeriodoAdapter(getActivity(),R.layout.list_periodo_spinner_item,periodos);
                        material_spinner.setAdapter(spinnerPeriodoAdapter);
                        callAvanceByPeriodo("DIAZPJOS", spinnerPeriodoAdapter.getItem(0).getAnnio(), spinnerPeriodoAdapter.getItem(0).getDescription());

                    }

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


    private void callAvanceByPeriodo(String usuario, String annio, String periodo) {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario", usuario);
        dataConsulta.put("annio", annio);
        dataConsulta.put("periodo", periodo);
        Call<AvanceResponse> avanceReponseCall = ApiRetrofitShort.getInstance(BASE_URL).getUserService().getAvanceByPeriodo(dataConsulta);
        avanceReponseCall.enqueue(new Callback<AvanceResponse>() {
            @Override
            public void onResponse(Call<AvanceResponse> call, Response<AvanceResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                 if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                     AvanceResponse avanceResponse =(AvanceResponse) response.body();
                    tvCountAvanceDias.setText(avanceResponse.getAvanceDias()+"");
                    tvTotalDias.setText(avanceResponse.getTotalDias()+"");
                    tvRestoDias.setText(avanceResponse.getRestoDias()+"");
                    showProgressDias(avanceResponse.getTotalDias()!=0?(100*avanceResponse.getAvanceDias())/avanceResponse.getTotalDias():0);
                    tvLinealidad.setText(avanceResponse.getLinealidad()+" %");
                    tvNecesidad.setText("S/."+avanceResponse.getNecesidadDiaria());
                    tvAtendidos.setText(avanceResponse.getClientesAtendidos()+"");
                    tvProyeccion.setText("S/."+avanceResponse.getProyeccion()+"");
                     ((ContenedorActivity) getActivity()).showProgress(false);

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
                     ((ContenedorActivity) getActivity()).showProgress(false);
                     ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);

                     AvanceResponse avanceResponse =new AvanceResponse(0,0,0,0.0,0.0,0,0,statusResponse);
                     tvCountAvanceDias.setText(avanceResponse.getAvanceDias()+"");
                     tvTotalDias.setText(avanceResponse.getTotalDias()+"");
                     tvRestoDias.setText(avanceResponse.getRestoDias()+"");
                     showProgressDias(avanceResponse.getTotalDias()!=0?(100*avanceResponse.getAvanceDias())/avanceResponse.getTotalDias():0);
                     tvLinealidad.setText(avanceResponse.getLinealidad()+" %");
                     tvNecesidad.setText("S/."+avanceResponse.getNecesidadDiaria());
                     tvAtendidos.setText(avanceResponse.getClientesAtendidos()+"");
                     tvProyeccion.setText("S/."+avanceResponse.getProyeccion()+"");


                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
                     ((ContenedorActivity) getActivity()).showProgress(false);
                     ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                 }

            }

            @Override
            public void onFailure(Call<AvanceResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
                ToastSilicon.toastDangerOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    private void showProgressDias(Integer porcentajeAvance) {
        pStatus = 0.0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus < porcentajeAvance) {
                    pStatus += 0.1;
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pbAvanceDia.setProgress(pStatus.intValue());
                        }
                    });
                }
            }
        }).start();
    }


}