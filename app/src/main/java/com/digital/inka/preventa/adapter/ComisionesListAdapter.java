package com.digital.inka.preventa.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.model.AvancePolitica;
import com.digital.inka.preventa.model.AvanceProveedor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComisionesListAdapter extends RecyclerView.Adapter<ComisionesListAdapter.ViewHolder> {

    private Context ctx;
    private List<AvancePolitica> items;
    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
    private int current_selected_idx = -1;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescComision;
        public TextView tvMonto;

        public ViewHolder(View view) {
            super(view);
            tvDescComision = (TextView) view.findViewById(R.id.tvDescComision);
            tvMonto = (TextView) view.findViewById(R.id.tvMonto);
        }

    }

    public ComisionesListAdapter(Context mContext, List<AvancePolitica> items) {
        this.ctx = mContext;
        this.items = items;
        selected_items = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comisiones, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AvancePolitica avancePolitica = items.get(position);

        holder.tvDescComision.setText(avancePolitica.getDescPolitica());
        holder.tvMonto.setText(avancePolitica.getMontoPolitica()+"");
    }


    public AvancePolitica getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getSelectedItemCount() {
        return selected_items.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        items.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        current_selected_idx = -1;
    }

    public interface OnClickListener {
        void onItemClick(View view, AvanceProveedor obj, int pos);

        void onItemLongClick(View view, AvanceProveedor obj, int pos);
    }
}