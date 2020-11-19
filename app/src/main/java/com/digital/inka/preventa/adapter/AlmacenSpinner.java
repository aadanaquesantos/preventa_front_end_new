package com.digital.inka.preventa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.digital.inka.R;
import com.digital.inka.preventa.model.Almacen;

import java.util.ArrayList;
import java.util.List;

public class AlmacenSpinner extends ArrayAdapter<Almacen> {

    public AlmacenSpinner(Context context,
                          List<Almacen> almacenes)
    {
        super(context, 0, almacenes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_almacen_spinner_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.tvDescAlmacen);
        Almacen currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName.setText(currentItem.getDescription());
        }
        return convertView;
    }
}