package com.digital.inka.preventa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.LoginRegistroActivity;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.User;
import com.digital.inka.preventa.model.UserResponse;
import com.digital.inka.preventa.util.TransformacionPunto;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    EditText etUserEmail;
    EditText etPassword;
    TextView btnSignUp;
    MaterialButton btnSignIn;
    ImageButton btnStatusEmail;
    ToolTipsManager mToolTipsManager;
    ConstraintLayout rootUserEmail;
    ToolTip.Builder builder;
    ImageButton btnStatusPass;
   TextInputLayout ilUserEmail;
    TextInputLayout ilPassword;
    private String username="";
    private String password="";
    private String mensaje="";

    public LoginFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
             username=getArguments().getString("username");
            password=getArguments().getString("password");
             mensaje=getArguments().getString("mensaje");
        }
    }

    private void bind(View rootView) {
        etUserEmail = rootView.findViewById(R.id.etUserEmail);
        etPassword = rootView.findViewById(R.id.etPassword);
        etPassword.setTransformationMethod(new TransformacionPunto());
        btnSignUp = rootView.findViewById(R.id.btnSignUp);
        btnSignIn = rootView.findViewById(R.id.btnSignIn);
        rootUserEmail = rootView.findViewById(R.id.rootUserEmail);
        btnStatusEmail = rootView.findViewById(R.id.btnStatusEmail);
         btnStatusPass = rootView.findViewById(R.id.btnStatusPass);
        ilUserEmail=rootView.findViewById(R.id.ilUserEmail);
         ilPassword = rootView.findViewById(R.id.ilPassword);
    }

    private void showPassword() {
        int start, end;
        start = etPassword.getSelectionStart();
        end = etPassword.getSelectionEnd();
        etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
      mToolTipsManager.dismiss(R.id.btnStatusPass);
        btnStatusEmail.setVisibility(View.GONE);
      ilPassword.setEndIconCheckable(false);
       ilPassword.setEndIconDrawable(R.drawable.openeye);
        etPassword.setSelection(start, end);
    }

    private void hidePassword() {
        int start, end;
        start = etPassword.getSelectionStart();
        end = etPassword.getSelectionEnd();
        etPassword.setTransformationMethod(new TransformacionPunto());
        mToolTipsManager.dismiss(R.id.btnStatusPass);
      btnStatusEmail.setVisibility(View.GONE);
      ilPassword.setEndIconCheckable(true);
      ilPassword.setEndIconDrawable(R.drawable.closeeye);
        etPassword.setSelection(start, end);
    }

    private void events() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LoginRegistroActivity) getActivity()).loadRegistroFragment();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateLogin()) {
                    callLogin();
                }

            }
        });
        etUserEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                     mToolTipsManager.dismissAll();
                }
            }
        });
        etUserEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
               validateUserEmail();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //here is your code
                validatePassword();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
        ilPassword.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ilPassword.isEndIconCheckable()) {
                    showPassword();
                } else {
                    hidePassword();
                }
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
       bind(rootView);
       events();
        mToolTipsManager = new ToolTipsManager();
        mToolTipsManager.findAndDismiss(btnStatusEmail);
        populateUserOk(username,password,mensaje);
        return rootView;
    }

    private void populateUserOk(String username,String password,String mensaje) {
        if(!mensaje.isEmpty()){
 //            new SnackAlert(getActivity())
//                    .setTitle("INFO")
//                    .setMessage(mensaje)
//                    .setType(SnackAlert.SUCCESS)
//                    .show();
            etUserEmail.setText(username);
            etPassword.setText(password);
        }

    }

    public void callLogin() {
        ((LoginRegistroActivity) getActivity()).showProgress(true);
        User user = new User(etUserEmail.getText().toString(), "", etPassword.getText().toString(), "", "", "", "");
        Call<UserResponse> loginCall = ApiRetrofitShort.getInstance(BASE_URL).getUserService().login(user);
        loginCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals("1")) {
                    User user = response.body().getUser();
                     System.out.println(user.getFullName());

//                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
//                    ((LoginRegistroActivity) getActivity()).showProgress(false);
//                    startActivity(intent);
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
                ((LoginRegistroActivity) getActivity()).showProgress(false);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                ((LoginRegistroActivity) getActivity()).showProgress(false);
//                new SnackAlert(getActivity())
//                        .setTitle("Error")
//                        .setMessage(t.getMessage())
//                        .setType(SnackAlert.ERROR)
//                        .show();
            }
        });
    }

    public boolean validateUserEmail() {
        boolean band = true;
        String emailUserText = etUserEmail.getText().toString().trim();
        if (emailUserText.isEmpty()) {
            //ilUserEmail.setError("ingrese usuario");
             btnStatusEmail.setImageResource(R.drawable.ic_error_outline);
             btnStatusEmail.setVisibility(View.VISIBLE);
             btnStatusEmail.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            builder = new ToolTip.Builder(getContext(), btnStatusEmail, rootUserEmail, "Ingrese usuario o email!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
           // builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band = false;
        } else {
            //ilUserEmail.setError(null);
             mToolTipsManager.dismiss(R.id.btnStatusEmail);
            btnStatusEmail.setVisibility(View.GONE);
        }
        return band;

    }

    public boolean validatePassword() {
        boolean band = true;
        String passwordText = etPassword.getText().toString().trim();
        if (passwordText.isEmpty()) {

                 btnStatusPass.setImageResource(R.drawable.ic_error_outline);
                 btnStatusPass.setVisibility(View.VISIBLE);
                 btnStatusPass.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            builder = new ToolTip.Builder(getContext(), btnStatusPass, rootUserEmail, "Ingrese su clave!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
            //builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band = false;
        } else {
      mToolTipsManager.dismiss(R.id.btnStatusPass);
             btnStatusPass.setVisibility(View.GONE);

        }
        return band;

    }

    public boolean validateLogin() {
        boolean band = true;
        String emailUserText = etUserEmail.getText().toString().trim();
        String passwordText = etPassword.getText().toString().trim();
        if (emailUserText.isEmpty()) {
            //ilUserEmail.setError("ddddddddddddddddd");
            btnStatusEmail.setImageResource(R.drawable.ic_error_outline);
            btnStatusEmail.setVisibility(View.VISIBLE);
            btnStatusEmail.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            builder = new ToolTip.Builder(getContext(), btnStatusEmail, rootUserEmail, "Ingrese usuario o email!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
           // builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band = false;
        } else {
           // ilUserEmail.setError(null);
           mToolTipsManager.dismiss(R.id.btnStatusEmail);
           btnStatusEmail.setVisibility(View.GONE);
        }
        if (passwordText.isEmpty()) {
             btnStatusPass.setImageResource(R.drawable.ic_error_outline);
             btnStatusPass.setVisibility(View.VISIBLE);
            btnStatusPass.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
            builder = new ToolTip.Builder(getContext(), btnStatusPass, rootUserEmail, "Ingrese su clave!", ToolTip.POSITION_ABOVE);
            builder.setGravity(ToolTip.GRAVITY_CENTER);
            //builder.setTextAppearance(R.style.TooltipTextAppearance);
            builder.setAlign(ToolTip.ALIGN_CENTER);
            mToolTipsManager.show(builder.build());
            band = false;
        } else {
            mToolTipsManager.dismiss(R.id.btnStatusPass);
          btnStatusPass.setVisibility(View.GONE);

        }
        return band;
    }
}