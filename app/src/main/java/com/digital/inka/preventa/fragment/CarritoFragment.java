package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.ComisionesListAdapter;
import com.digital.inka.preventa.adapter.SpinnerPeriodoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.AvancePolitica;
import com.digital.inka.preventa.model.AvancePoliticaListResponse;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.Periodo;
import com.digital.inka.preventa.model.PeriodoListResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.google.android.material.button.MaterialButton;
import com.rabbil.toastsiliconlibrary.ToastSilicon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends DialogFragment {
    private RecyclerView recyclerView;
    private ComisionesListAdapter mAdapter;
    private TextView tvTitle;
    private AppCompatSpinner material_spinner;
    private MaterialButton ivClose;
    private Double pStatus = 0.0;
    private Handler handler = new Handler();
     public CarritoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CarritoFragment newInstance(String param1, String param2) {
        CarritoFragment fragment = new CarritoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_carrito, container, false);
        tvTitle=view.findViewById(R.id.titleToolbarDialog);
        ivClose=view.findViewById(R.id.ivClose);
         tvTitle.setText("Carrito de compras");
//        recyclerView = (RecyclerView) view.findViewById(R.id.rvDirecciones);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//         recyclerView.setHasFixedSize(true);

        showDialogAddCarrito();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  dismiss();
                getActivity().onBackPressed();

            }
        });


       return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }



    private Dialog showDialogAddCarrito() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_carrito);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;



        final TextView titleTelefono = (TextView) dialog.findViewById(R.id.txtDialogTitleTelefono);
        //  titleTelefono.setText(c.getDescCliente());
        ((ImageButton) dialog.findViewById(R.id.bt_cerrar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//
        final EditText editTextTelefono = (EditText) dialog.findViewById(R.id.etNroTelefono);
        ((AppCompatButton) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nroTelefono = editTextTelefono.getText().toString().trim();
                editTextTelefono.setError(null);
                boolean cancel = false;
                View focusView = null;
                if (TextUtils.isEmpty(nroTelefono)) {
                    editTextTelefono.setError("Ingrese Teléfono");
                    focusView = editTextTelefono;
                    cancel = true;
                }




                if (cancel) {
                    focusView.requestFocus();
                } else {

                    new SweetAlertDialog(v.getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("CONFIRMACIÓN")
                            .setContentText("Usted registrará el  numero telefónico  ")
                            .setConfirmText("SI")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismissWithAnimation();
                                    dialog.dismiss();

                                }
                            })
                            .setCancelText("NO")
                            .show();
                }
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
        return dialog;
    }


}