package com.digital.inka.preventa.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.activity.LoginRegistroActivity;
import com.digital.inka.preventa.model.SueldoResponse;
import com.digital.inka.preventa.util.UtilAndroid;
import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuAvanceVentasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuAvanceVentasFragment extends Fragment {

    private TextView txtProgress;
    private ProgressBar progressBar;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
    private MaterialButton btnGo;
    static Double porcentajeAvance;

    public MenuAvanceVentasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MenuVentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuAvanceVentasFragment newInstance(Double avanceCuota) {
        porcentajeAvance=avanceCuota;
      MenuAvanceVentasFragment fragment = new MenuAvanceVentasFragment();
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
View view=inflater.inflate(R.layout.fragment_menu_venta, container, false);

        txtProgress = (TextView) view.findViewById(R.id.txtProgress);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        btnGo=(MaterialButton)view.findViewById(R.id.btnGo);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.printf("total"+porcentajeAvance);
//                while (pStatus <= porcentajeAvance) {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setProgress(pStatus);
//                            txtProgress.setText(pStatus + " %");
//                        }
//                    });
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    pStatus=++;
//                }
//            }
//        }).start();

showProgressCuota(porcentajeAvance);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContenedorActivity) getActivity()).loadAvanceFragment();
            }
        });
        return view;
    }

    private void showProgressCuota(Double porcentajeAvance) {
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
                            progressBar.setProgress(pStatus.intValue());
                            txtProgress.setText(UtilAndroid.round(pStatus, 3) + "%");

                        }
                    });
                }
            }
        }).start();
    }
}