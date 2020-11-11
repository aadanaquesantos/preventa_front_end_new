package com.digital.inka.preventa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digital.inka.R;
import com.digital.inka.preventa.model.Almacen;
import com.digital.inka.preventa.model.CondicionPago;

import java.util.List;

public class SpinneCondicionAdapter extends ArrayAdapter<CondicionPago> {

    private static final String TAG = "SpinnerSimpleAdapter";
    private List<CondicionPago> condiciones;
    private Activity activity;

    public SpinneCondicionAdapter(Activity a, int textViewResourceId, List<CondicionPago> condiciones) {
        super(a, textViewResourceId, condiciones);
        this.condiciones = condiciones;
        activity = a;
    }

    public static class ViewHolderPeriodo {
        public TextView descCondicion;
    }

    SpinneCondicionAdapter.ViewHolderPeriodo holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_condicion_spinner_item, null);
            holder = new SpinneCondicionAdapter.ViewHolderPeriodo();
            holder.descCondicion = (TextView) v.findViewById(R.id.tvDescCondicion);
            v.setTag(holder);
        } else holder = (SpinneCondicionAdapter.ViewHolderPeriodo)v.getTag();
        CondicionPago condicionPago =  condiciones.get(position);
        if (condicionPago != null) {
            holder.descCondicion.setText(condicionPago.getDescription());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

}
