package com.digital.inka.preventa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.digital.inka.R;
import com.digital.inka.preventa.model.CondicionPago;
import com.digital.inka.preventa.model.TipoDoc;

import java.util.List;

public class SpinneTipoDocsAdapter extends ArrayAdapter<TipoDoc> {

    private static final String TAG = "SpinnerSimpleAdapter";
    private List<TipoDoc> tipoDocs;
    private Activity activity;

    public SpinneTipoDocsAdapter(Activity a, int textViewResourceId, List<TipoDoc> tipoDocs) {
        super(a, textViewResourceId, tipoDocs);
        this.tipoDocs = tipoDocs;
        activity = a;
    }

    public static class ViewHolderPeriodo {
        public TextView descTipoDoc;
    }

    SpinneTipoDocsAdapter.ViewHolderPeriodo holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_tipodocs_spinner_item, null);
            holder = new SpinneTipoDocsAdapter.ViewHolderPeriodo();
            holder.descTipoDoc = (TextView) v.findViewById(R.id.tvDescTipoDoc);
            v.setTag(holder);
        } else holder = (SpinneTipoDocsAdapter.ViewHolderPeriodo)v.getTag();
        TipoDoc tipoDoc =  tipoDocs.get(position);
        if (tipoDoc != null) {
            holder.descTipoDoc.setText(tipoDoc.getDescription());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

}
