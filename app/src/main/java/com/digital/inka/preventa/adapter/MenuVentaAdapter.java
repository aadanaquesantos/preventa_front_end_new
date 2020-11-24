package com.digital.inka.preventa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.digital.inka.R;
import com.digital.inka.preventa.activity.base.BaseActivity;
import com.digital.inka.preventa.fragment.LoginFragment;
import com.digital.inka.preventa.fragment.MenuAvancePorProveedorFragment;
import com.digital.inka.preventa.fragment.MenuAvanceVentasFragment;
import com.digital.inka.preventa.model.MenuDashboard;
import com.digital.inka.preventa.model.SueldoResponse;
import com.digital.inka.preventa.util.ViewIdGenerator;

import java.util.ArrayList;

public class MenuVentaAdapter extends RecyclerView.Adapter<MenuVentaAdapter.MenuVentaViewHolder> {
    ArrayList<MenuDashboard> menuVentaList=new ArrayList<>();
    Context context;
    SueldoResponse sueldoResponse;
    FragmentManager fragmentManager;


    public MenuVentaAdapter(Context context, ArrayList<MenuDashboard> menuVentaList, SueldoResponse sueldoResponse, FragmentManager fragmentManager) {
        this.menuVentaList = menuVentaList;
        this.context=context;
        this.sueldoResponse=sueldoResponse;
        this.fragmentManager=fragmentManager;
     }

    @NonNull
    @Override
    public MenuVentaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_menu_ventas,parent,false);
        MenuVentaViewHolder menuVentaViewHolder=new MenuVentaViewHolder(view);
        return menuVentaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuVentaViewHolder holder, int position) {
        MenuDashboard producto=menuVentaList.get(position);
        //FragmentManager fm=((BaseActivity)context).getSupportFragmentManager();
        int newContainerId = ViewIdGenerator.generateViewId();
        if(producto.getCodMenu().equals("01")){
            holder.containerMenuVenta.setId(newContainerId);// Set container id
            MenuAvanceVentasFragment  menuAvanceVentasFragment = (MenuAvanceVentasFragment) fragmentManager.findFragmentById(newContainerId);
            if (menuAvanceVentasFragment == null) {
                menuAvanceVentasFragment = MenuAvanceVentasFragment.newInstance(sueldoResponse.getAvanceCuota());
                fragmentManager .beginTransaction()
                        .add(newContainerId, menuAvanceVentasFragment).addToBackStack(null)
                        .commit();

            }
        }else if(producto.getCodMenu().equals("02")){
            holder.containerMenuVenta.setId(newContainerId);// Set container id
            MenuAvancePorProveedorFragment  menuAvancePorProveedorFragment = (MenuAvancePorProveedorFragment) fragmentManager.findFragmentById(newContainerId);
            if (menuAvancePorProveedorFragment == null) {
                menuAvancePorProveedorFragment = MenuAvancePorProveedorFragment.newInstance();
                fragmentManager .beginTransaction()
                        .add(newContainerId, menuAvancePorProveedorFragment).addToBackStack(null)
                        .commit();
            }

        }

    }

    @Override
    public int getItemCount() {
        return menuVentaList.size();
    }


    public static class MenuVentaViewHolder extends RecyclerView.ViewHolder{
        FrameLayout containerMenuVenta;
        TextView nombre;
        TextView descripcion;
        TextView precio;


        public MenuVentaViewHolder(@NonNull View itemView) {
            super(itemView);
            //hoocks
            containerMenuVenta=itemView.findViewById(R.id.containerMenuVenta);
//            nombre=itemView.findViewById(R.id.tvNombreProdPop);
//            descripcion=itemView.findViewById(R.id.tvDescProdPop);
//            precio=itemView.findViewById(R.id.tvPrecioPop);
         }
    }
}
