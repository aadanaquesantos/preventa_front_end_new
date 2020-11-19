package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.CustomerLocalListAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.CustomerLocal;
import com.digital.inka.preventa.model.CustomerLocalListResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.SueldoResponse;
import com.digital.inka.preventa.widget.LineItemDecoration;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.rabbil.toastsiliconlibrary.ToastSilicon;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerLocalListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerLocalListFragment extends Fragment {

    private RecyclerView recyclerView;
    private CustomerLocalListAdapter mAdapter;
    private ActionMode actionMode;
    private ProgressDialog pd;
    Dialog dialog;
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    private AppCompatEditText etSearchCliente;

    public CustomerLocalListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CustomerLocalListFragment newInstance() {
        CustomerLocalListFragment fragment = new CustomerLocalListFragment();
      //  Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
      //  fragment.setArguments(args);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_local_list, container, false);
        //View viewFloating = getActivity().findViewById(R.id.floatings);
        //viewFloating.setVisibility(View.VISIBLE);
        dialog = onCreateDialog();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       recyclerView.addItemDecoration(new LineItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);
        bottom_sheet =v.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);

//        etSearchCliente=v.findViewById(R.id.etSearchCliente);
 
//        etSearchCliente.requestFocus();
//        etSearchCliente.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(hasFocus){
//                     InputMethodManager im = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    im.showSoftInput(etSearchCliente, 0);
//                }
//            }
//        });
       // loadRoutesDia();
        cargarClientes();
        return v;
    }

    private void loadRoutesDia() {
    }

    public Dialog onCreateDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setTitle(null);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        return dialog;
    }

    private void cargarClientes() {
        //dialog.show();
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario", "DIAZPJOS");
        Call<CustomerLocalListResponse> loginCall = ApiRetrofitShort.getInstance(BASE_URL).getCustomerService().getClientesByDia(dataConsulta);
        loginCall.enqueue(new Callback<CustomerLocalListResponse>() {
            @Override
            public void onResponse(Call<CustomerLocalListResponse> call, Response<CustomerLocalListResponse> response) {
               // if(response.body()!=null){
                //    StatusResponse statusResponse = response.body().getStatus();
                    //if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                        List<CustomerLocal> items = response.body().getCustomerLocals();
                        //set data and list adapter
                        mAdapter = new CustomerLocalListAdapter(getContext(), items);
                        recyclerView.setAdapter(mAdapter);
                        mAdapter.setOnClickListener(new CustomerLocalListAdapter.OnClickListener() {
                            @Override
                            public void onItemClick(View view, CustomerLocal obj, int pos) {

                            }

                            @Override
                            public void onItemLongClick(View view, CustomerLocal obj, int pos) {
                                showBottomSheetDialog(obj);
                            }
                        });
                        ((ContenedorActivity) getActivity()).showProgress(false);// verificar

//                    } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {
//                        ((ContenedorActivity) getActivity()).showProgress(false);
//                        ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
//
//                    } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {
//                        ((ContenedorActivity) getActivity()).showProgress(false);
//                        ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
//                    }
//
//                }else{
//                    ToastSilicon.toastDangerOne(getActivity(),"SERVICIO DETENIDO!!", Toast.LENGTH_SHORT);
//                }


            }

            @Override
            public void onFailure(Call<CustomerLocalListResponse> call, Throwable t) {
                ToastSilicon.toastWarningOne(getActivity(),t.getMessage(), Toast.LENGTH_SHORT);
                ((ContenedorActivity) getActivity()).showProgress(false);

            }
        });
    }

    private void showBottomSheetDialog(final CustomerLocal customerLocal) {
        mBehavior.setSkipCollapsed(true);
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        final View view = getLayoutInflater().inflate(R.layout.sheet_list, null);
       TextView txtDescCliente=((TextView) view.findViewById(R.id.txtDescCliente));
        txtDescCliente.setText(customerLocal.getCustomer().getDescription());

        ((View) view.findViewById(R.id.lyt_infoCustomer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.setEnabled(false);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBottomSheetDialog.dismiss();//verificar
                        view.setEnabled(true);
                        ((ContenedorActivity) getActivity()).loadCustomerInfoFragment(customerLocal);
                    }
                },150); //150 is in milliseconds

            }
        });
//
        ((View) view.findViewById(R.id.lyt_pedidos)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setEnabled(false);
                view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBottomSheetDialog.dismiss();//verificar
                        view.setEnabled(true);
                        ((ContenedorActivity) getActivity()).loadDatosPedidoFragment(customerLocal);
                    }
                },150); //150 is in milliseconds

            }
        });
//
//        ((View) view.findViewById(R.id.lyt_get_link)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Get link '" + people.name + "' clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        ((View) view.findViewById(R.id.lyt_make_copy)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Make a copy '" + people.name + "' clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

        mBottomSheetDialog = new BottomSheetDialog(getActivity());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }
}