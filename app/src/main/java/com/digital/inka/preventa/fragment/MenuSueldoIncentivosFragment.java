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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuSueldoIncentivosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuSueldoIncentivosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();

    public MenuSueldoIncentivosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MenuVentaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuSueldoIncentivosFragment newInstance() {
      MenuSueldoIncentivosFragment fragment = new MenuSueldoIncentivosFragment();
//        Bundle args = new Bundle();
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
View view=inflater.inflate(R.layout.fragment_menu_sueldo_incentivos, container, false);


        return view;
    }
}