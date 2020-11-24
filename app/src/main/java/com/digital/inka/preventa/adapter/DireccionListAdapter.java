package com.digital.inka.preventa.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.model.Customer;
import com.digital.inka.preventa.model.DispatchAddress;
import com.digital.inka.preventa.util.CircleImageView;

import java.util.ArrayList;
import java.util.List;

public class DireccionListAdapter extends RecyclerView.Adapter<DireccionListAdapter.ViewHolder> {

    private Context ctx;
    private List<DispatchAddress> items;
    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
    private int current_selected_idx = -1;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDivision, tvEmpresa,tvDescDirDespacho,tvRuta;
        public ImageView ivStatus;


        public View lyt_parent;

        public ViewHolder(View view) {
            super(view);

            tvDivision = (TextView) view.findViewById(R.id.tvDivision);
            tvEmpresa = (TextView) view.findViewById(R.id.tvEmpresa);
            tvDescDirDespacho=(TextView) view.findViewById(R.id.tvDescDirDespacho);
            tvRuta=(TextView) view.findViewById(R.id.tvRuta);
            ivStatus=(ImageView)view.findViewById(R.id.ivStatus);

            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }

    }

    public DireccionListAdapter(Context mContext, List<DispatchAddress> items) {
        this.ctx = mContext;
        this.items = items;
        selected_items = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_direcciones, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DispatchAddress dispatchAddress = items.get(position);

        holder.tvDivision.setText(dispatchAddress.getRoute().getDivision().getDescription());
        holder.tvEmpresa.setText(dispatchAddress.getRoute().getCompany().getDescription());
        holder.tvDescDirDespacho.setText(dispatchAddress.getDescription());
        holder.tvRuta.setText(dispatchAddress.getRoute().getDescription());
//        holder.lyt_parent.setActivated(selected_items.get(position, false));
//        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onClickListener == null) return;
//                onClickListener.onItemClick(v, customer, position);
//            }
//        });

//        holder.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (onClickListener == null) return false;
//                onClickListener.onItemLongClick(v, customer, position);
//                return true;
//            }
//        });
//
       displayImage(holder, dispatchAddress);

    }

    private void displayImage(ViewHolder holder, DispatchAddress direccion) {
      if (direccion.getStatusLocal().equals("A")) {
          holder.ivStatus.setColorFilter(ContextCompat.getColor(ctx.getApplicationContext(), R.color.green_A200), android.graphics.PorterDuff.Mode.MULTIPLY);

      }else if (direccion.getStatusLocal().equals("I")){
          holder.ivStatus.setColorFilter(ContextCompat.getColor(ctx.getApplicationContext(), R.color.red_500), android.graphics.PorterDuff.Mode.MULTIPLY);

     }
    }


    public DispatchAddress getItem(int position) {
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
        void onItemClick(View view, Customer obj, int pos);

        void onItemLongClick(View view, Customer obj, int pos);
    }
}