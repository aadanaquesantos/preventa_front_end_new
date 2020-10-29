package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.digital.inka.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvanceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvanceFragment extends DialogFragment {

private TextView tvTitle;
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
       return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}