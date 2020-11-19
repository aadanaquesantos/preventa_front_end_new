package com.digital.inka.preventa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.digital.inka.R;
import com.digital.inka.preventa.model.Product;

import java.util.ArrayList;

public class ArticuloAdapter extends RecyclerView.Adapter<ArticuloAdapter.ArticuloViewHolder> {
    ArrayList<Product> productos=new ArrayList<>();
    Context context;

    private OnClickListener onClickListener = null;

    public void setOnClickListener(ArticuloAdapter.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    public ArticuloAdapter(Context context, ArrayList<Product> productos) {
        this.productos = productos;
        this.context=context;
    }

    @NonNull
    @Override
    public ArticuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_articulo_item,parent,false);
        ArticuloViewHolder articuloViewHolder=new ArticuloViewHolder(view);
        return articuloViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticuloViewHolder holder, int position) {
        Product product=productos.get(position);
        Glide.with(context).load(product.getUri()).placeholder(R.drawable.article)
        .override(100, 200) // resizes the image to these dimensions (in pixel)
//                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
        //apply(new RequestOptions().override(1000, 1000))
//        Glide.with(context)
//                .asBitmap()
//                .load(product.getUri())
//               // .error(R.drawable.no_result)
//                .override(holder.cvCategoria.getWidth(),holder.cvCategoria.getHeight())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new CustomTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
//                        int width = resource.getWidth();
//                        int height = resource.getHeight();
//                        System.out.println(width+"*"+height);
//                        holder.image.setImageBitmap(resource);
//                       // holder.image.buildDrawingCache();
//
//                    }
//                    @Override
//                    public void onLoadCleared(@Nullable Drawable placeholder) { }
//                });


         holder.title.setText(product.getDescription());
        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, product, position);
            }
        });
      }

    @Override
    public int getItemCount() {
        return productos.size();
    }


    public static class ArticuloViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title;
        CardView lyt_parent;


        public ArticuloViewHolder(@NonNull View itemView) {
            super(itemView);
            //hoocks
            image=itemView.findViewById(R.id.ivImagen);
            title=itemView.findViewById(R.id.tvTitle);
            lyt_parent=itemView.findViewById(R.id.lyt_parent);

        }
    }

    public interface OnClickListener {
        void onItemClick(View view, Product obj, int pos);

        void onItemLongClick(View view, Product obj, int pos);
    }
}
