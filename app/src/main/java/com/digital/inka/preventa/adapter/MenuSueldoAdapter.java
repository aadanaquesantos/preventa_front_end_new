package com.digital.inka.preventa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.base.BaseActivity;
import com.digital.inka.preventa.fragment.MenuAvancePorProveedorFragment;
import com.digital.inka.preventa.fragment.MenuAvanceVentasFragment;
import com.digital.inka.preventa.fragment.MenuSueldoBasicoFragment;
import com.digital.inka.preventa.fragment.MenuSueldoComisionesFragment;
import com.digital.inka.preventa.fragment.MenuSueldoIncentivosFragment;
import com.digital.inka.preventa.model.MenuDashboard;
import com.digital.inka.preventa.util.ViewIdGenerator;

import java.util.ArrayList;

public class MenuSueldoAdapter extends RecyclerView.Adapter<MenuSueldoAdapter.MenuVentaViewHolder> {
    ArrayList<MenuDashboard> menuVentaList=new ArrayList<>();
    Context context;

    public MenuSueldoAdapter(Context context, ArrayList<MenuDashboard> menuVentaList) {
        this.menuVentaList = menuVentaList;
        this.context=context;
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
        FragmentManager fm=((BaseActivity)context).getSupportFragmentManager();
        int newContainerId = ViewIdGenerator.generateViewId();
        if(producto.getCodMenu().equals("03")){
            holder.containerMenuVenta.setId(newContainerId);// Set container id
            MenuSueldoBasicoFragment menuSueldoBasicoFragment = (MenuSueldoBasicoFragment) fm.findFragmentById(newContainerId);
            if (menuSueldoBasicoFragment == null) {
                menuSueldoBasicoFragment = MenuSueldoBasicoFragment.newInstance();
                fm .beginTransaction()
                        .add(newContainerId, menuSueldoBasicoFragment).addToBackStack(null)
                        .commit();

            }

        }else if(producto.getCodMenu().equals("04")){
            holder.containerMenuVenta.setId(newContainerId);// Set container id
            MenuSueldoComisionesFragment menuSueldoComisionesFragment = (MenuSueldoComisionesFragment) fm.findFragmentById(newContainerId);
            if (menuSueldoComisionesFragment == null) {
                menuSueldoComisionesFragment = MenuSueldoComisionesFragment.newInstance();
                fm .beginTransaction()
                        .add(newContainerId, menuSueldoComisionesFragment).addToBackStack(null)
                        .commit();
            }
        }else if(producto.getCodMenu().equals("05")){
            holder.containerMenuVenta.setId(newContainerId);// Set container id
            MenuSueldoIncentivosFragment menuSueldoIncentivosFragment = (MenuSueldoIncentivosFragment) fm.findFragmentById(newContainerId);
            if (menuSueldoIncentivosFragment == null) {
                menuSueldoIncentivosFragment = MenuSueldoIncentivosFragment.newInstance();
                fm .beginTransaction()
                        .add(newContainerId, menuSueldoIncentivosFragment).addToBackStack(null)
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
