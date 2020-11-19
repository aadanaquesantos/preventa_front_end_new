package com.digital.inka.preventa.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.digital.inka.R;
import com.digital.inka.preventa.model.Carrito;
import com.digital.inka.preventa.model.CarritoList;
import com.digital.inka.preventa.model.DetallePedido;
import com.digital.inka.preventa.model.Pedido;
import com.digital.inka.preventa.util.SessionUsuario;
import com.digital.inka.preventa.util.UtilAndroid;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.DetallePedidoViewHolder>  {
   List<DetallePedido> detallePedidos=new ArrayList<>();
    Context context;
    Activity activity;
    SessionUsuario sessionUsuario;
    private OnClickListener onClickListener = null;
     private TextView tvTotal;



    public void setOnClickListener(DetallePedidoAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public DetallePedidoAdapter(Context context,List<DetallePedido> detallePedidos, Activity activity) {
        this.detallePedidos =detallePedidos;
        this.context=context;
        this.activity=activity;
        sessionUsuario=new SessionUsuario(activity);
    }

    @NonNull
    @Override
    public DetallePedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pedido_item,parent,false);
        DetallePedidoViewHolder detallePedidoViewHolder=new DetallePedidoViewHolder(view);
        return detallePedidoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetallePedidoViewHolder holder, int position) {
        DetallePedido detallePedido=detallePedidos.get(position);
        Glide.with(context).load(detallePedido.getProduct().getUri()).placeholder(R.drawable.article)
        .override(100, 200) // resizes the image to these dimensions (in pixel)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
         holder.tvDescArticulo.setText(detallePedido.getProduct().getDescription());
        holder.tvCantidad.setText(detallePedido.getCantidad()+"");
         holder.tvUnidadMedida.setText(detallePedido.getProduct().getUm());
         holder.tvSubTotal.setText(detallePedido.getImporteTotal()+"");
         if(detallePedido.getImporteDescuentos()>0){
             holder.lblDctos.setVisibility(View.VISIBLE);
             holder.tvDctos.setVisibility(View.VISIBLE);
             holder.tvDctos.setText(detallePedido.getImporteDescuentos()+"");
         }

    }

    @Override
    public int getItemCount() {
        return detallePedidos.size();
    }
    public Double getTotal(){
        Double montoVenta=new Double(0);
        for (int i = 0; i <detallePedidos.size() ; i++) {
            montoVenta=montoVenta+detallePedidos.get(i).getCantidad()*detallePedidos.get(i).getProduct().getPriceSugerido();

        }

        return UtilAndroid.round(montoVenta,2);
    }



    public static class DetallePedidoViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tvDescArticulo;
        TextView tvUnidadMedida;
        TextView tvCantidad;
        TextView tvSubTotal;
        TextView lblDctos;
        TextView tvDctos;


        public DetallePedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            //hoocks
            image=itemView.findViewById(R.id.ivImagen);
            tvDescArticulo=itemView.findViewById(R.id.tvDescArticulo);
            tvUnidadMedida=itemView.findViewById(R.id.tvUnidadMedida);
            tvCantidad=itemView.findViewById(R.id.tvCantidad);
            tvSubTotal=itemView.findViewById(R.id.tvSubTotal);
            lblDctos=itemView.findViewById(R.id.lblDctos);
            tvDctos=itemView.findViewById(R.id.tvDctos);

        }
    }

    public interface OnClickListener {
        void onItemClick(View view, DetallePedido obj, int pos);

        void onItemLongClick(View view, DetallePedido obj, int pos);
    }
}
