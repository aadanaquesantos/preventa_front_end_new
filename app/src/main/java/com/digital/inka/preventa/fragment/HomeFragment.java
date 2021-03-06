package com.digital.inka.preventa.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.MenuSueldoAdapter;
import com.digital.inka.preventa.adapter.MenuVentaAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.MenuDashboard;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.SueldoResponse;
import com.rabbil.toastsiliconlibrary.ToastSilicon;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import org.jetbrains.annotations.NotNull;

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
    private ConstraintLayout lyt_parent;
    private ProgressBar pbLoadVentas;
    private ProgressBar pbLoadSueldos;
    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        callGetSueldoCuotaAvance();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (getArguments() != null) {
         //   mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        HomeFragment fragment;
//        if (savedInstanceState != null) {
//            fragment = (HomeFragment) getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment");
//        }
//        else {
//            fragment = new HomeFragment();
//            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, fragment, "HomeFragment").commit();
//        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_home, container, false);
        rvMenuVenta=root.findViewById(R.id.rvMenuVentas);
        rvMenuSueldo=root.findViewById(R.id.rvMenuSueldos);
        lyt_parent=root.findViewById(R.id.lyt_parent);
        pbLoadVentas=root.findViewById(R.id.pbLoadVentas);
        pbLoadSueldos=root.findViewById(R.id.pbLoadSueldos);
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
                if(response.body()!=null){
                    StatusResponse statusResponse = response.body().getStatus();
                    if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                        if(getActivity()==null){
                            System.out.println(getActivity());
                        }
                        ((ContenedorActivity) getActivity()).showProgress(false);// verificar

                        loadMenus(response.body());
                    } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
                        ((ContenedorActivity) getActivity()).showProgress(false);
                        ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                        loadMenus(new SueldoResponse(0.0,0.0,0.0,statusResponse));

                    } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
                        ((ContenedorActivity) getActivity()).showProgress(false);
                        ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                    }

                }else{
                    ToastSilicon.toastDangerOne(getActivity(),"SERVICIO DETENIDO!!", Toast.LENGTH_SHORT);
                }

            }

            @Override
            public void onFailure(Call<SueldoResponse> call, Throwable t) {
                ((ContenedorActivity) getActivity()).showProgress(false);
                ToastSilicon.toastDangerOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
            }
        });


    }

    private void loadMenus(SueldoResponse sueldoResponse){
        rvMenuVenta.setHasFixedSize(true);
        rvMenuVenta.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<MenuDashboard> menuVentas=new ArrayList<>();
        menuVentas.add(new MenuDashboard("01","Pizza Clásica","Salsa clásica de la casa","$",new Double(12.58),R.drawable.pizza_clasica));
        menuVentas.add(new MenuDashboard("02","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
        menuVentaAdapter=new MenuVentaAdapter(getContext(),menuVentas,sueldoResponse,getChildFragmentManager());
        rvMenuVenta.setAdapter(menuVentaAdapter);
        pbLoadVentas.setVisibility(View.GONE);
        rvMenuVenta.setVisibility(View.VISIBLE);

        rvMenuSueldo.setHasFixedSize(true);
        rvMenuSueldo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<MenuDashboard> menuSueldos=new ArrayList<>();
        menuSueldos.add(new MenuDashboard("03","Pizza Clásica","Salsa clásica de la casa","$",new Double(12.58),R.drawable.pizza_clasica));
        menuSueldos.add(new MenuDashboard("04","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
        menuSueldos.add(new MenuDashboard("05","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));
        menuSueldos.add(new MenuDashboard("06","Hamburguesa mix","Doble carne con queso","$",new Double(12.58),R.drawable.hamburguesa_mix_img));

        menuSueldoAdapter=new MenuSueldoAdapter(getContext(),menuSueldos,sueldoResponse,getChildFragmentManager());
        rvMenuSueldo.setAdapter(menuSueldoAdapter);
        pbLoadSueldos.setVisibility(View.GONE);
        rvMenuSueldo.setVisibility(View.VISIBLE);
        lyt_parent.setVisibility(View.VISIBLE);
    }

}