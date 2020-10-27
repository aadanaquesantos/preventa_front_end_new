package com.digital.inka.preventa.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.LoginRegistroActivity;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.User;
import com.digital.inka.preventa.model.UserResponse;
import com.google.android.material.button.MaterialButton;

import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;


import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {


    MaterialButton btnSignUp;
    MaterialButton btnSignIn;
    MaterialButton btnSearchDni;
    EditText etDni;
    EditText etFullName;
    EditText etUsername;
    EditText etEmail;
    EditText etPassword;
    EditText etRePassword;
    ToolTipsManager mToolTipsManager;
    ToolTip.Builder builder;
    RelativeLayout rootSignUp;
    EditText etCelular;

    public RegistroFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance() {
        RegistroFragment fragment = new RegistroFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void bind(View rootView){
        btnSignUp = rootView.findViewById(R.id.btnSignUp);
        btnSignIn = rootView.findViewById(R.id.btnSignIn);
        btnSearchDni = rootView.findViewById(R.id.btnSearchDni);
        etDni = rootView.findViewById(R.id.etDni);
        etFullName = rootView.findViewById(R.id.etFullName);
        etUsername = rootView.findViewById(R.id.etUsername);
        etEmail = rootView.findViewById(R.id.etEmail);
        etPassword=rootView.findViewById(R.id.etPassword);
        etRePassword=rootView.findViewById(R.id.etRePassword);
        rootSignUp=rootView.findViewById(R.id.rootSignUp);
        etCelular=rootView.findViewById(R.id.etCelular);
    }
    private void events(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateSignUp()){

                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Confirmación")
//                            .setContentText("esta seguro d?")
                            .setConfirmText("SI")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    callRestUpdateUser();
                                }
                            })
                            .setCancelButton("NO", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();

                }

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginRegistroActivity) getActivity()).loadLoginFragment();
            }
        });
        btnSearchDni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDni()){
                    mToolTipsManager.dismissAll();
                    callRestUserByEmail();
                }

            }
        });
    }

    private void callRestUpdateUser() {
      ((LoginRegistroActivity) getActivity()).showProgress(true);
       User user=new User("","",etPassword.getText().toString().trim(),etUsername.getText().toString().trim(),"","",etCelular.getText().toString().trim());
        Call<StatusResponse> callUpdateUser = ApiRetrofitShort.getInstance(BASE_URL).getUserService().updateUserLogin(user);
        callUpdateUser.enqueue(new Callback<StatusResponse>() {
            @Override
            public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                StatusResponse statusResponse = response.body();
                if (statusResponse.getStatusCode().equals("1")) {
                    Bundle bundle=new Bundle();
                    bundle.putString("username",etUsername.getText().toString());
                    bundle.putString("password",etPassword.getText().toString());
                    bundle.putString("mensaje",statusResponse.getStatusText());
                    ((LoginRegistroActivity) getActivity()).loadLoginFragmentConParametros(bundle);

                } else if (statusResponse.getStatusCode().equals("0")) {
                    clearFormSignUp();
//                    new SnackAlert(getActivity())
//                            .setTitle("Alerta")
//                            .setMessage(statusResponse.getStatusText())
//                            .setType(SnackAlert.WARNING)
//                            .show();
                } else if (statusResponse.getStatusCode().equals("-1")) {
                    clearFormSignUp();
//                    new SnackAlert(getActivity())
//                            .setTitle("Error")
//                            .setMessage(statusResponse.getStatusText())
//                            .setType(SnackAlert.ERROR)
//                            .show();
                }
                ((LoginRegistroActivity) getActivity()).showProgress(false);


            }

            @Override
            public void onFailure(Call<StatusResponse> call, Throwable t) {
                clearFormSignUp();
                ((LoginRegistroActivity) getActivity()).showProgress(false);
//                new SnackAlert(getActivity())
//                        .setTitle("Error")
//                        .setMessage(t.getMessage())
//                        .setType(SnackAlert.ERROR)
//                        .show();
            }
        });
    }

    private boolean validateEqualsRePass(String password,String rePassword) {
        boolean band=true;
        if(password!=null || !password.isEmpty()){
            if(rePassword!=null || !rePassword.isEmpty()){
                if(!password.equals(rePassword)){
                    band=false;

                }

            }
        }

        return band;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registro, container, false);
         bind(rootView);
        events();
        mToolTipsManager = new ToolTipsManager();
        return rootView;
    }

    private void callRestUserByEmail() {
        ((LoginRegistroActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("dni", etDni.getText().toString());
        Call<UserResponse> callUserByDni = ApiRetrofitShort.getInstance(BASE_URL).getUserService().userByEmail(dataConsulta);
        callUserByDni.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals("1")) {
                    User user = response.body().getUser();
                    populateFormSignUp(user);
                } else if (statusResponse.getStatusCode().equals("0")) {
                    clearFormSignUp();
//                    new SnackAlert(getActivity())
//                            .setTitle("Alerta")
//                            .setMessage(statusResponse.getStatusText())
//                            .setType(SnackAlert.WARNING)
//                            .show();
                } else if (statusResponse.getStatusCode().equals("-1")) {
                    clearFormSignUp();
//                    new SnackAlert(getActivity())
//                            .setTitle("Error")
//                            .setMessage(statusResponse.getStatusText())
//                            .setType(SnackAlert.ERROR)
//                            .show();
                }
                ((LoginRegistroActivity) getActivity()).showProgress(false);


            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                clearFormSignUp();
                ((LoginRegistroActivity) getActivity()).showProgress(false);
//                new SnackAlert(getActivity())
//                        .setTitle("Error")
//                        .setMessage(t.getMessage())
//                        .setType(SnackAlert.ERROR)
//                        .show();
            }
        });
    }

    private void populateFormSignUp(User user) {
        etFullName.setText(user.getFullName());
        etUsername.setText(user.getUsername());
        etEmail.setText(user.getEmail());
    }

    private void clearFormSignUp() {
        etFullName.getText().clear();
        etUsername.getText().clear();
        etEmail.getText().clear();
    }

    private boolean validateDni(){
        boolean band=true;
        String dniText=etDni.getText().toString().trim();
        if(dniText.isEmpty()){
            builder = new ToolTip.Builder(getContext(), etDni, rootSignUp, "Ingrese un Dni!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
           // builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band=false;
        }else{
            mToolTipsManager.dismiss(R.id.etDni);
        }
        return band;
    }
    private boolean validateSignUp(){
        boolean band=true;
        String dniText=etDni.getText().toString().trim();
        String passwordText=etPassword.getText().toString().trim();
        String rePasswordText=etRePassword.getText().toString().trim();
        String celularText=etCelular.getText().toString().trim();
        if(dniText.isEmpty()){
            builder = new ToolTip.Builder(getContext(), etDni, rootSignUp, "Ingrese Dni!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
           // builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band=false;
        }else{
            mToolTipsManager.dismiss(R.id.etDni);
        }
        if(passwordText.isEmpty()){
            builder = new ToolTip.Builder(getContext(), etPassword, rootSignUp, "Ingrese  clave!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
            //builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band=false;
        }else{
            mToolTipsManager.dismiss(R.id.etPassword);
        }

        if(rePasswordText.isEmpty()){
            builder = new ToolTip.Builder(getContext(), etRePassword, rootSignUp, "Repita la  clave!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
           // builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band=false;
        }else{
            mToolTipsManager.dismiss(R.id.etRePassword);
        }

        if(celularText.isEmpty()){
            builder = new ToolTip.Builder(getContext(), etCelular, rootSignUp, "Ingrese número de celular!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
           // builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band=false;
        }else{
            mToolTipsManager.dismiss(R.id.etCelular);
        }
        //if(!etPassword.getText().toString().isEmpty() && !!etRePassword.getText().toString().isEmpty() ){
            if(!validateEqualsRePass(passwordText,rePasswordText)){
                builder = new ToolTip.Builder(getContext(), etRePassword, rootSignUp, "Claves no coinciden!!", ToolTip.POSITION_ABOVE);
                builder.setGravity(ToolTip.GRAVITY_CENTER);
              //  builder.setTextAppearance(R.style.TooltipTextAppearance);
                builder.setAlign(ToolTip.ALIGN_CENTER);
                mToolTipsManager.show(builder.build());
                band=false;
            }else{
                mToolTipsManager.dismiss(R.id.etRePassword);
            }
       // }


        return band;
    }

    private void resetFormSignUp(){
        etDni.getText().clear();
        etFullName.getText().clear();
        etUsername.getText().clear();
        etPassword.getText().clear();
        etRePassword.getText().clear();
        etEmail.getText().clear();
        etCelular.getText().clear();
    }
}