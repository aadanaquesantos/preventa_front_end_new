package com.digital.inka.preventa.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.base.BaseActivity;
import com.digital.inka.preventa.fragment.AvanceFragment;
import com.digital.inka.preventa.fragment.AvanceProveedorFragment;
import com.digital.inka.preventa.fragment.CarritoFragment;
import com.digital.inka.preventa.fragment.ComisionesFragment;
import com.digital.inka.preventa.fragment.CustomerInfoFragment;
import com.digital.inka.preventa.fragment.CustomerLocalListFragment;
import com.digital.inka.preventa.fragment.DatosPedidoFragment;
import com.digital.inka.preventa.fragment.DialogAddCarritoFragment;
import com.digital.inka.preventa.fragment.DialogPrevisualizarFragment;
import com.digital.inka.preventa.fragment.HomeFragment;
import com.digital.inka.preventa.model.CarritoRequest;
import com.digital.inka.preventa.model.Customer;
import com.digital.inka.preventa.model.CustomerLocal;
import com.digital.inka.preventa.model.Pedido;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContenedorActivity extends BaseActivity {
    private View customToolbar;
    private CardView cardViewBottomMenu;
    private BottomNavigationView navigation;

    AvanceFragment avanceFragment = new AvanceFragment();
    AvanceProveedorFragment avanceProveedorFragment=new AvanceProveedorFragment();
    ComisionesFragment comisionesFragment=new ComisionesFragment();
    CustomerInfoFragment customerInfoFragment=new CustomerInfoFragment();
    DatosPedidoFragment datosPedidoFragment=new DatosPedidoFragment();
    CarritoFragment carritoFragment=new CarritoFragment();

    DialogPrevisualizarFragment dialogPrevisualizarFragment=new DialogPrevisualizarFragment();
    DialogAddCarritoFragment dialogAddCarritoFragment=new DialogAddCarritoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenedor);
        initComponent();
        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, homeFragment,"HomeFragment").
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }
    private void initComponent() {
        customToolbar = (View) findViewById(R.id.customToolbar);
        cardViewBottomMenu=findViewById(R.id.cardViewBottomMenu);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.goHome:
                        item.setEnabled(false);
                        navigation.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                HomeFragment homeFragment = new HomeFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, homeFragment,"HomeFragment").
                                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                                item.setEnabled(true);
                            }
                        },150); //150 is in milliseconds

                        return true;
                    case R.id.goCustomer:
                        CustomerLocalListFragment customerListFragment = new CustomerLocalListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, customerListFragment,"CustomerListFragment").
                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
                        return true;

                }
                return false;
            }
        });

    }

    public void loadAvanceFragment() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (avanceFragment.isAdded()) {
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, avanceFragment).addToBackStack(null)  //VERIFICAR
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, avanceFragment).addToBackStack(null)
                    .commit();
        }
     }



    public void loadAddCarritoFragment(CarritoRequest carritoRequest, DialogAddCarritoFragment.CallbackAddCarrito callbackAddCarrito) {

       //getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
       DialogAddCarritoFragment.newInstance(carritoRequest);
        dialogAddCarritoFragment.setCallbackAddCarrito(callbackAddCarrito);
        if ( dialogAddCarritoFragment.isAdded()) {
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, dialogAddCarritoFragment).addToBackStack("CarritoFragment")  //VERIFICAR
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, dialogAddCarritoFragment).addToBackStack("CarritoFragment")
                    .commit();
        }
    }

public void loadCarritoFragment(CarritoRequest carritoRequest){
    getSupportFragmentManager().popBackStackImmediate("CarritoFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    CarritoFragment.newInstance(carritoRequest);
    if (carritoFragment.isAdded()) {
        getSupportFragmentManager().executePendingTransactions();
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.contenedorFragment, carritoFragment).addToBackStack("CarritoFragment")  //VERIFICAR
                .commit();

    } else {
        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, carritoFragment).addToBackStack(null)
                .commit();
    }

}

    public void loadPrevisualizarFragment(Pedido pedido){
        getSupportFragmentManager().popBackStackImmediate("DialogPrevisualizarFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        DialogPrevisualizarFragment.newInstance(pedido);
        if (dialogPrevisualizarFragment.isAdded()) {
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager()
                    .beginTransaction().replace(R.id.contenedorFragment, dialogPrevisualizarFragment).addToBackStack("DialogPrevisualizarFragment")  //VERIFICAR
                    .commit();

        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, dialogPrevisualizarFragment).addToBackStack(null)
                    .commit();
        }

    }



    private Fragment recreateFragment(Fragment f)
    {
        try {
            Fragment.SavedState savedState = getSupportFragmentManager().saveFragmentInstanceState(f);

            Fragment newInstance = f.getClass().newInstance();
            newInstance.setInitialSavedState(savedState);

            return newInstance;
        }
        catch (Exception e) // InstantiationException, IllegalAccessException
        {
            throw new RuntimeException("Cannot reinstantiate fragment " + f.getClass().getName(), e);
        }
    }
    public void loadAvanceProveedorFragment() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (avanceProveedorFragment.isAdded()) {
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, avanceProveedorFragment).addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, avanceProveedorFragment).addToBackStack(null)
                    .commit();
        }
    }

    public void loadComisionesFragment() {
        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (comisionesFragment.isAdded()) {
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, comisionesFragment).addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, comisionesFragment).addToBackStack(null)
                    .commit();
        }
    }

    public void loadCustomerInfoFragment(CustomerLocal customerLocal) {
        Bundle bundle=new Bundle();
        bundle.putString("codCliente",customerLocal.getCustomer().getCode());
        customerInfoFragment.setArguments(bundle);
        if (customerInfoFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, customerInfoFragment).addToBackStack(null)
                    .commit();
        } else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, customerInfoFragment).addToBackStack(null)
                    .commit();
        }
    }

    public void loadDatosPedidoFragment(CustomerLocal customerLocal) {
        Bundle bundle=new Bundle();
        bundle.putString("codCliente",customerLocal.getCustomer().getCode());
        bundle.putString("codLocal",customerLocal.getDispatchAddress().getCode());
        datosPedidoFragment.setArguments(bundle);
        if (datosPedidoFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, datosPedidoFragment).addToBackStack(null)
                    .commit();
        } else {

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, datosPedidoFragment).addToBackStack(null)
                    .commit();
        }
    }


    boolean exit=false;
    @Override
    public void onBackPressed() {

      int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 6) {
//            if (exit) {
                   finish(); // finish activity
//            } else {
//
//                exit = true;
//
//
          }else{
            getSupportFragmentManager().popBackStackImmediate();
           int c = getSupportFragmentManager().getBackStackEntryCount();
        }
//        } else {
//            getSupportFragmentManager().popBackStackImmediate();
//            int c = getSupportFragmentManager().getBackStackEntryCount();
//        }

    }
}