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
import com.digital.inka.preventa.fragment.CarritoFragment;
import com.digital.inka.preventa.model.Carrito;
import com.digital.inka.preventa.model.CarritoList;
import com.digital.inka.preventa.util.SessionUsuario;
import com.digital.inka.preventa.util.UtilAndroid;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder> implements CarritoFragment.CallbackUpdateRecycler {
    ArrayList<Carrito> carritoList=new ArrayList<>();
    Context context;
    Activity activity;
    SessionUsuario sessionUsuario;
    private OnClickListener onClickListener = null;
    private CarritoFragment.CallbackUpdateRecycler callbackUpdateRecycler;
    private TextView tvTotal;


    public void setCallbackUpdateRecycler(CarritoFragment.CallbackUpdateRecycler callbackUpdateRecycler) {
        this.callbackUpdateRecycler = callbackUpdateRecycler;
    }

    public void setOnClickListener(CarritoAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public CarritoAdapter(Context context, ArrayList<Carrito> carritoList, Activity activity,TextView tvTotal) {
        this.carritoList = carritoList;
        this.context=context;
        this.activity=activity;
        this.tvTotal=tvTotal;
        sessionUsuario=new SessionUsuario(activity);
//        Double monto=new Double(0);
//        for (int i = 0; i <sessionUsuario.getCarritoList().getListCarrito().size() ; i++) {
//            monto=
//        }
//        tvTotal.setText("S/."+UtilAndroid.round();

    }

    @NonNull
    @Override
    public CarritoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_carrito_item,parent,false);
        CarritoViewHolder carritoViewHolder=new CarritoViewHolder(view);
        return carritoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoViewHolder holder, int position) {
        Carrito carrito=carritoList.get(position);
        Glide.with(context).load(carritoList.get(position).getProduct().getUri()).placeholder(R.drawable.article)
        .override(100, 200) // resizes the image to these dimensions (in pixel)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
         holder.tvDescArticulo.setText(carrito.getProduct().getDescription());
         holder.etCantidad.setText(carrito.getCantidad()+"");
         holder.tvUnidadMedida.setText(carrito.getProduct().getUm());
         holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, carrito, position);
            }
        });

         holder.ivDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new MaterialAlertDialogBuilder(activity,R.style.AlertDialogTheme)
                         //.setTitle("Desea eliminar del carrito?")
                         .setMessage("Retirar el articulo "+carrito.getProduct().getDescription()+" de su carrito de compras ?")
                         .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 carritoList.remove(position);
                                 notifyItemRemoved(position);
                                 CarritoList paqueteCarrito=new CarritoList();
                                 paqueteCarrito.setListCarrito(carritoList);
                                 sessionUsuario.saveCarritoList(paqueteCarrito);
                                 notifyItemRangeChanged(position, carritoList.size());

                             }
                         })
                         .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                             }
                         })
                         .show();
             }
         });
         holder.tvSubTotal.setText("S/."+UtilAndroid.round(carrito.getCantidad()*carrito.getProduct().getPriceSugerido(),2));
         holder.ibMas.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 carrito.setCantidad(carrito.getCantidad()+1);
                 carrito.setImporte(carrito.getCantidad()*carrito.getImporte());
                 holder.etCantidad.setText(carrito.getCantidad()+"");
                 holder.tvSubTotal.setText("S/."+UtilAndroid.round(carrito.getCantidad()*carrito.getProduct().getPriceSugerido(),2)+"");
                 ArrayList<Carrito> listaAux=sessionUsuario.getCarritoList().getListCarrito();
                 listaAux.set(position,carrito);
                 CarritoList paqueteCarrito=new CarritoList();
                 paqueteCarrito.setListCarrito(listaAux);
                 sessionUsuario.saveCarritoList(paqueteCarrito);

                 Double monto=new Double(0);
                 for (int i = 0; i <listaAux.size() ; i++) {
                     monto=(listaAux.get(i).getProduct().getPriceSugerido()*listaAux.get(i).getCantidad())+monto;
                 }
                 tvTotal.setText("S/."+UtilAndroid.round(monto,2));
             }
         });
        holder.ibMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrito.setCantidad(carrito.getCantidad()-1<=0?0:carrito.getCantidad()-1);
                carrito.setImporte(carrito.getCantidad()*carrito.getImporte());
                holder.etCantidad.setText((carrito.getCantidad())+"");
                holder.tvSubTotal.setText("S/."+UtilAndroid.round(carrito.getCantidad()*carrito.getProduct().getPriceSugerido(),2)+"");
                ArrayList<Carrito> listaAux=sessionUsuario.getCarritoList().getListCarrito();
                listaAux.set(position,carrito);
                CarritoList paqueteCarrito=new CarritoList();
                paqueteCarrito.setListCarrito(listaAux);
                sessionUsuario.saveCarritoList(paqueteCarrito);
                Double monto=new Double(0);
                for (int i = 0; i <listaAux.size() ; i++) {
                    monto=(listaAux.get(i).getProduct().getPriceSugerido()*listaAux.get(i).getCantidad())+monto;
                }
                tvTotal.setText("S/."+UtilAndroid.round(monto,2));
            }
        });

        holder.etCantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){//TIENE FOCUS
                    Toast.makeText(activity,"s",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(activity,"a",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return carritoList.size();
    }
    public Double getTotal(){
        Double montoVenta=new Double(0);
        for (int i = 0; i <carritoList.size() ; i++) {
            montoVenta=montoVenta+carritoList.get(i).getCantidad()*carritoList.get(i).getProduct().getPriceSugerido();

        }

        return UtilAndroid.round(montoVenta,2);
    }

    @Override
    public void updateRecycler(Carrito carrito) {
        Toast.makeText(context,"sasas",Toast.LENGTH_SHORT).show();
    }


    public static class CarritoViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView tvDescArticulo;
        TextView tvUnidadMedida;
        EditText etCantidad;
        CardView lyt_parent;
        ImageView ivDelete;
        ImageButton ibMas;
        ImageButton ibMenos;
        TextView tvSubTotal;


        public CarritoViewHolder(@NonNull View itemView) {
            super(itemView);
            //hoocks
            image=itemView.findViewById(R.id.ivImagen);
            tvDescArticulo=itemView.findViewById(R.id.tvDescArticulo);
            tvUnidadMedida=itemView.findViewById(R.id.tvUnidadMedida);
            etCantidad=itemView.findViewById(R.id.etCantidad);
            lyt_parent=itemView.findViewById(R.id.lyt_parent);
            ivDelete=itemView.findViewById(R.id.ivDelete);
            ibMas=itemView.findViewById(R.id.ibMas);
            ibMenos=itemView.findViewById(R.id.ibMenos);
            tvSubTotal=itemView.findViewById(R.id.tvSubTotal);

        }
    }

    public interface OnClickListener {
        void onItemClick(View view, Carrito obj, int pos);

        void onItemLongClick(View view, Carrito obj, int pos);
    }
}
