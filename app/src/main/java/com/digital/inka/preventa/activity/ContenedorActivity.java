package com.digital.inka.preventa.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.base.BaseActivity;
import com.digital.inka.preventa.fragment.AvanceFragment;
import com.digital.inka.preventa.fragment.HomeFragment;
import com.digital.inka.preventa.fragment.RegistroFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContenedorActivity extends BaseActivity {
    private View customToolbar;
    private CardView cardViewBottomMenu;
    private BottomNavigationView navigation;

    AvanceFragment avanceFragment = new AvanceFragment();
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
//                switch (item.getItemId()) {
//                    case R.id.goHome:
//                        HomeFragment homeFragment = new HomeFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, homeFragment,"HomeFragment").
//                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//                        return true;
//                    case R.id.goCustomer:
//                        //  actionBar.setTitle("CLIENTES");
//                        CustomerListFragment customerListFragment = new CustomerListFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, customerListFragment,"CustomerListFragment").
//                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//                        return true;
//                    case R.id.goPerfil:
//                        //  actionBar.setTitle("CLIENTES");
//                        AvanceFragment avanceFragment = new AvanceFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment, avanceFragment,"AvanceFragment").
//                                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
//                        return true;
////                    case R.id.navigation_nearby:
////                        mTextMessage.setText(item.getTitle());
////                        return true;
//                }
                return false;
            }
        });

    }

    public void loadAvanceFragment() {
        if (avanceFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedorFragment, avanceFragment).addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, avanceFragment).addToBackStack(null)
                    .commit();
        }
    }


}