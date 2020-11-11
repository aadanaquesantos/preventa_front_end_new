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
import com.digital.inka.preventa.model.Route;

import java.util.List;

public class SpinnerRouteAdapter extends ArrayAdapter<Route> {

    private static final String TAG = "SpinnerSimpleAdapter";
    private List<Route> routes;
    private Activity activity;

    public SpinnerRouteAdapter(Activity a, int textViewResourceId, List<Route> routes) {
        super(a, textViewResourceId, routes);
        this.routes = routes;
        activity = a;
    }

    public static class ViewHolderRoute {
        public TextView descRuta;
    }

    SpinnerRouteAdapter.ViewHolderRoute holder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.list_route_spinner_item, null);
            holder = new SpinnerRouteAdapter.ViewHolderRoute();
            holder.descRuta = (TextView) v.findViewById(R.id.tvDescRoute);
            v.setTag(holder);
        } else holder = (SpinnerRouteAdapter.ViewHolderRoute)v.getTag();
        Route route =  routes.get(position);
        if (route != null) {
            holder.descRuta.setText(route.getDescription());
        }
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position,convertView,parent);
    }

}
