package com.digital.inka.preventa.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.activity.LoginRegistroActivity;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.SueldoResponse;
import com.digital.inka.preventa.model.User;
import com.digital.inka.preventa.model.UserRequest;
import com.digital.inka.preventa.model.UserResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuSueldoBasicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuSueldoBasicoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();
    private TextView tvSueldo;
     static String textoMontoSueldo;

    public MenuSueldoBasicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MenuVentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuSueldoBasicoFragment newInstance(Double sueldo) {
      MenuSueldoBasicoFragment fragment = new MenuSueldoBasicoFragment();
        textoMontoSueldo="S /. "+sueldo;
//             Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        View view=inflater.inflate(R.layout.fragment_menu_sueldo_basico, container, false);
        tvSueldo=view.findViewById(R.id.tvSueldo);
        tvSueldo.setText(textoMontoSueldo);


        return view;
    }


}