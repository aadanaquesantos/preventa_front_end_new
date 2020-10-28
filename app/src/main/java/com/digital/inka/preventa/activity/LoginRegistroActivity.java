package com.digital.inka.preventa.activity;

import android.os.Bundle;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.base.BaseActivity;
import com.digital.inka.preventa.fragment.LoginFragment;
import com.digital.inka.preventa.fragment.RegistroFragment;


public class LoginRegistroActivity extends BaseActivity {
    LoginFragment loginFragment = new LoginFragment();
    RegistroFragment registroFragment = new RegistroFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registro);
        loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, loginFragment).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public boolean providesActivityToolbar() {
        return false;
    }

    public void loadRegistroFragment() {
        if (registroFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, registroFragment).addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, registroFragment).addToBackStack(null)
                    .commit();
        }
    }

    public void loadLoginFragment() {
        if (loginFragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, loginFragment).addToBackStack(null)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, loginFragment).addToBackStack(null)
                    .commit();
        }
    }

    public void loadLoginFragmentConParametros(Bundle argumentos) {
        loginFragment = new LoginFragment();
        loginFragment.setArguments(argumentos);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, loginFragment).addToBackStack(null)
                .commit();
    }
}