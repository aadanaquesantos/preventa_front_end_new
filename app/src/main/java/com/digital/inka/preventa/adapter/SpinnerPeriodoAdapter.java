package com.digital.inka.preventa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.digital.inka.R;
import com.digital.inka.preventa.model.Periodo;

import java.util.ArrayList;
import java.util.List;

public class SpinnerPeriodoAdapter extends ArrayAdapter<Periodo> {

    private static final String TAG = "SpinnerSimpleAdapter";
    private List<Periodo> periodos;
    private Activity activity;

    public SpinnerPeriodoAdapter(Activity a, int textViewResourceId, List<Periodo> periodos) {
        super(a, textViewResourceId, periodos);
        this.periodos = periodos;
        activity = a;
    }

    public static class ViewHolderPeriodo {
        public TextView descPeriodo;
    }

    SpinnerPeriodoAdapter.ViewHolderPeriodo holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_periodo_spinner_item, null);
            holder = new SpinnerPeriodoAdapter.ViewHolderPeriodo();
            holder.descPeriodo = (TextView) v.findViewById(R.id.tvDescPeriodo);
            v.setTag(holder);
        } else holder = (SpinnerPeriodoAdapter.ViewHolderPeriodo)v.getTag();
        Periodo periodo =  periodos.get(position);
        if (periodo != null) {
            holder.descPeriodo.setText(periodo.getDescription());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

}
