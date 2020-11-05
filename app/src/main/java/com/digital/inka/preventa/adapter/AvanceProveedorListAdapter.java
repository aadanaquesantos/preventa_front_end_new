package com.digital.inka.preventa.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.digital.inka.preventa.model.AvanceProveedor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AvanceProveedorListAdapter extends RecyclerView.Adapter<AvanceProveedorListAdapter.ViewHolder> {

    private Context ctx;
    private List<AvanceProveedor> items;
    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
    private int current_selected_idx = -1;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvProveedor;
        public TextView tvAvance;
        public ProgressBar pbAvance;

        public View lyt_parent;

        public ViewHolder(View view) {
            super(view);
            tvProveedor = (TextView) view.findViewById(R.id.tvProveedor);
            tvAvance = (TextView) view.findViewById(R.id.tvAvance);
            pbAvance=(ProgressBar) view.findViewById(R.id.pbAvance);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }

    }

    public AvanceProveedorListAdapter(Context mContext, List<AvanceProveedor> items) {
        this.ctx = mContext;
        this.items = items;
        selected_items = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_avance_proveedor, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AvanceProveedor avanceProveedor = items.get(position);

        holder.tvProveedor.setText(avanceProveedor.getCodProveedor()+"-"+avanceProveedor.getDescProveedor());
        holder.tvAvance.setText(avanceProveedor.getAvance()+"%");
        Drawable bckgrndDr =ctx.getResources().getDrawable(R.color.commonColorWhite);
        Drawable secProgressDr = ctx.getResources().getDrawable(R.color.gray_80);
        int[] androidColors = ctx.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        Drawable progressDr = new ScaleDrawable(new ColorDrawable(randomAndroidColor), Gravity.LEFT, 1, -1);
        LayerDrawable resultDr = new LayerDrawable(new Drawable[] { bckgrndDr, secProgressDr, progressDr });
        resultDr.setId(0, android.R.id.background);
        resultDr.setId(1, android.R.id.secondaryProgress);
        resultDr.setId(2, android.R.id.progress);
        holder.pbAvance.setProgressDrawable(resultDr);
        showProgress(avanceProveedor.getAvance(),holder.pbAvance);


    }
    private void showProgress(Double porcentajeAvance,ProgressBar pb) {
        pStatus = 0.0;
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (pStatus < porcentajeAvance) {
//                    pStatus += 0.1;
//                    try {
//                        Thread.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            pb.setProgress(pStatus.intValue());
//                        }
//                    });
//                }
//            }
//        }).start();
        pb.setProgress(porcentajeAvance.intValue());
    }

    public AvanceProveedor getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void toggleSelection(int pos) {
        current_selected_idx = pos;
        if (selected_items.get(pos, false)) {
            selected_items.delete(pos);
        } else {
            selected_items.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
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