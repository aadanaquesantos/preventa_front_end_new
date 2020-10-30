package com.digital.inka.preventa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.MenuSueldoAdapter;
import com.digital.inka.preventa.adapter.MenuVentaAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.MenuDashboard;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.SueldoResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView rvMenuVenta;
    private RecyclerView rvMenuSueldo;
    MenuVentaAdapter menuVentaAdapter;
    MenuSueldoAdapter menuSueldoAdapter;



    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         //   mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        rvMenuVenta=root.findViewById(R.id.rvMenuVentas);
        rvMenuSueldo=root.findViewById(R.id.rvMenuSueldos);
        View viewFloating= getActivity().findViewById(R.id.floatings);
        viewFloating.setVisibility(View.INVISIBLE);
      callGetSueldoCuotaAvance();
        return root;
    }

    private void callGetSueldoCuotaAvance() {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario", "DIAZPJOS");
        Call<SueldoResponse> loginCall = ApiRetrofitShort.getInstance(BASE_URL).getUserService().getSueldo(dataConsulta);
        loginCall.enqueue(new Callback<SueldoResponse>() {
            @Override
            public void onResponse(Call<SueldoResponse> call, Response<SueldoResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals("1")) {
//                    Double  sueldo = response.body().getSueldo();
//                    Double cuota=response.body().getCuota();
//                    Double avanceCuota=response.body().getAvanceCuota();
                        loadMenus(response.body());

                } else if (statusResponse.getStatusCode().equals("0")) {
//                    new SnackAlert(getActivity())
//                            .setTitle("Alerta")
//                            .setMessage(statusResponse.getStatusText())
//                            .setType(SnackAlert.WARNING)
//                            .show();
                } else if (statusResponse.getStatusCode().equals("-1")) {
//                    new SnackAlert(getActivity())
//                            .setTitle("Error")
//                            .setMessage(statusResponse.getStatusText())
//                            .setType(SnackAlert.ERROR)
//                            .show();
                }
                ((ContenedorActivity) getActivity()).showProgress(false);
            }

            @Override
            public void onFailure(Call<SueldoResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
//                new SnackAlert(getActivity())
//                        .setTitle("Error")
//                        .setMessage(t.getMessage())
//                        .setType(SnackAlert.ERROR)
//                        .show();
            }
        });


    }

    private void loadMenus(SueldoResponse sueldoResponse){
        rvMenuVenta.setHasFixedSize(true);
        rvMenuVenta.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<MenuDashboard> menuVentas=new ArrayList<>();
        menuVentas.add(new MenuDashboard("01","Pizza Clásica","Salsa clásica de la casa","$",new Double(12.58),R.drawable.pizza_clasica));
        menuVentas.add(new MenuDashboard("02","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
        menuVentaAdapter=new MenuVentaAdapter(getContext(),menuVentas,sueldoResponse);
        rvMenuVenta.setAdapter(menuVentaAdapter);

        rvMenuSueldo.setHasFixedSize(true);
        rvMenuSueldo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<MenuDashboard> menuSueldos=new ArrayList<>();
        menuSueldos.add(new MenuDashboard("03","Pizza Clásica","Salsa clásica de la casa","$",new Double(12.58),R.drawable.pizza_clasica));
        menuSueldos.add(new MenuDashboard("04","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
        menuSueldos.add(new MenuDashboard("05","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
        menuSueldos.add(new MenuDashboard("06","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));

        menuSueldoAdapter=new MenuSueldoAdapter(getContext(),menuSueldos,sueldoResponse);
        rvMenuSueldo.setAdapter(menuSueldoAdapter);
    }
//    private void menuVentasRecycler(){
//        rvMenuVenta.setHasFixedSize(true);
//        rvMenuVenta.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//        ArrayList<MenuDashboard> menuVentas=new ArrayList<>();
//        menuVentas.add(new MenuDashboard("01","Pizza Clásica","Salsa clásica de la casa","$",new Double(12.58),R.drawable.pizza_clasica));
//        menuVentas.add(new MenuDashboard("02","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
//        menuVentaAdapter=new MenuVentaAdapter(getContext(),menuVentas);
//        rvMenuVenta.setAdapter(menuVentaAdapter);
//    }

//    private void menuSueldosRecycler(){
//        rvMenuSueldo.setHasFixedSize(true);
//        rvMenuSueldo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
//        ArrayList<MenuDashboard> menuVentas=new ArrayList<>();
//        menuVentas.add(new MenuDashboard("03","Pizza Clásica","Salsa clásica de la casa","$",new Double(12.58),R.drawable.pizza_clasica));
//        menuVentas.add(new MenuDashboard("04","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
//        menuVentas.add(new MenuDashboard("05","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
//        menuVentas.add(new MenuDashboard("06","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
//
//        menuSueldoAdapter=new MenuSueldoAdapter(getContext(),menuVentas);
//        rvMenuSueldo.setAdapter(menuSueldoAdapter);
//    }

}