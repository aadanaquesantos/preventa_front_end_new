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
import com.digital.inka.preventa.model.Periodo;

import java.util.List;

public class SpinnerAlmacenAdapter extends ArrayAdapter<Almacen> {

    private static final String TAG = "SpinnerSimpleAdapter";
    private List<Almacen> almacenes;
    private Activity activity;

    public SpinnerAlmacenAdapter(Activity a, int textViewResourceId, List<Almacen> almacenes) {
        super(a, textViewResourceId, almacenes);
        this.almacenes = almacenes;
        activity = a;
    }

    public static class ViewHolderPeriodo {
        public TextView descAlmacen;
    }

    SpinnerAlmacenAdapter.ViewHolderPeriodo holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_almacen_spinner_item, null);
            holder = new SpinnerAlmacenAdapter.ViewHolderPeriodo();
            holder.descAlmacen = (TextView) v.findViewById(R.id.tvDescAlmacen);
            v.setTag(holder);
        } else holder = (SpinnerAlmacenAdapter.ViewHolderPeriodo)v.getTag();
        Almacen almacen =  almacenes.get(position);
        if (almacen != null) {
            holder.descAlmacen.setText(almacen.getDescription());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

}
