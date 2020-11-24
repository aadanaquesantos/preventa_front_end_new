package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.ArticuloAdapter;
import com.digital.inka.preventa.adapter.CustomerLocalListAdapter;
import com.digital.inka.preventa.adapter.SugeridoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Carrito;
import com.digital.inka.preventa.model.CarritoList;
import com.digital.inka.preventa.model.CarritoRequest;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.CustomerLocal;
import com.digital.inka.preventa.model.DisponiblePrecioResponse;
import com.digital.inka.preventa.model.Product;
import com.digital.inka.preventa.model.ProductListResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.util.SessionUsuario;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.rabbil.toastsiliconlibrary.ToastSilicon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;


public class DialogAddCarritoFragment extends DialogFragment {

    public CallbackResult callbackResult;
    private RecyclerView rvSugeridos;
    private RecyclerView rvArticulos;
    SugeridoAdapter sugeridoAdapter;
    ArticuloAdapter articuloAdapter;
    EditText etSearchArticulo;
    private static CarritoRequest carritoRequestStatic;
    private TextView tvDisponible;
    private TextView tvPrecio;
    private EditText etCantidad;
    private MaterialButton btnAddCarrito;

    private Product productSelected;
    private CallbackAddCarrito callbackAddCarrito;

    public CallbackAddCarrito getCallbackAddCarrito() {
        return callbackAddCarrito;
    }

    public void setCallbackAddCarrito(CallbackAddCarrito callbackAddCarrito) {
        this.callbackAddCarrito = callbackAddCarrito;
    }

    public void setCallbackResult(CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    public void setOnCallbackResult(final CallbackResult callbackResult) {
        this.callbackResult = callbackResult;
    }

    private int request_code = 0;
    private View root_view;




    public static DialogAddCarritoFragment newInstance(CarritoRequest carritoRequest) {
        DialogAddCarritoFragment fragment = new DialogAddCarritoFragment();
         carritoRequestStatic=carritoRequest;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionUsuario sessionUsuario=new SessionUsuario(getContext());
        root_view = inflater.inflate(R.layout.dialog_add_carrito, container, false);
        rvSugeridos=root_view.findViewById(R.id.rvSugeridos);
        rvArticulos=root_view.findViewById(R.id.rvArticulos);
        etSearchArticulo=root_view.findViewById(R.id.etSearchArticulo);
        tvDisponible=root_view.findViewById(R.id.tvDisponible);
        tvPrecio=root_view.findViewById(R.id.tvPrecio);
        btnAddCarrito=root_view.findViewById(R.id.btnAddCarrito);
        etCantidad=root_view.findViewById(R.id.etCantidad);

        ImageButton dismiss = root_view.findViewById(R.id.bt_cerrar);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productSelected=null;
              dismiss();
            }
        });
         keyArticulos();
        callSugeridos();
        btnAddCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Carrito carrito=new Carrito(productSelected,new Integer(etCantidad.getText().toString()),productSelected.getPriceSugerido()*new Integer(etCantidad.getText().toString()));
                if(sessionUsuario.getCarritoList()==null){
                     CarritoList carritoList=new CarritoList();
                        carritoList.getListCarrito().add(carrito);
                        sessionUsuario.saveCarritoList(carritoList);
                }else{
                    CarritoList carritoList=sessionUsuario.getCarritoList();
                    carritoList.getListCarrito().add(carrito);
                    sessionUsuario.saveCarritoList(carritoList);

                }
                callbackAddCarrito.updateRecycler(carrito);
                productSelected=null;
                dismiss();
            }
        });
         return root_view;
    }

    public void keyArticulos() {
        etSearchArticulo.addTextChangedListener(new TextWatcher() {
            String ba = "";

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                //mostrarIconoClear();
                if (etSearchArticulo.getText().length() >= ba.length()) {
                    if (cs.toString().length() >= 4) {
                        callArticulos(cs.toString());
                    }

                }
            }


            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                //mostrarIconoClear();
                ba = arg0.toString();

            }

            @Override
            public void afterTextChanged(Editable arg0) {
//                if (descArticulo.getText().toString().length() <= 0) {
//                  recicladorArticulo.setVisibility(View.GONE);
//
//                }
            }
        });
    }
    private void callSugeridos() {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario",carritoRequestStatic.getUsuario());
        dataConsulta.put("codCliente",carritoRequestStatic.getCodCliente());
        dataConsulta.put("page", "1");
        dataConsulta.put("limit", "10");
        Call<ProductListResponse> callUpdateUser = ApiRetrofitShort.getInstance(BASE_URL).getProductService().getSugeridos(dataConsulta);
        callUpdateUser.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    rvSugeridos.setHasFixedSize(true);
                    rvSugeridos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                    sugeridoAdapter=new SugeridoAdapter(getContext(),response.body().getProducts());
                    rvSugeridos.setAdapter(sugeridoAdapter);

                    ((ContenedorActivity) getActivity()).showProgress(false);

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {

                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastWarningOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);

                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {

                    ((ContenedorActivity) getActivity()).showProgress(false);
                    ToastSilicon.toastDangerOne(getActivity(),statusResponse.getStatusText(), Toast.LENGTH_SHORT);
                }


            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {


            }
        });
    }

    private void callArticulos(String filtro) {
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario",carritoRequestStatic.getUsuario());
        dataConsulta.put("codEmpresa",carritoRequestStatic.getCodEmpresa());
        dataConsulta.put("codAlmacen",carritoRequestStatic.getCodAlmacen());
        dataConsulta.put("codLista",carritoRequestStatic.getCodLista());
        dataConsulta.put("filtro",filtro);
        dataConsulta.put("page", "1");
        dataConsulta.put("limit", "5");
        Call<ProductListResponse> callUpdateUser = ApiRetrofitShort.getInstance(BASE_URL).getProductService().getArticulos(dataConsulta);
        callUpdateUser.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    rvArticulos.setHasFixedSize(true);
                    rvArticulos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    articuloAdapter=new ArticuloAdapter(getContext(),response.body().getProducts());
                    rvArticulos.setAdapter(articuloAdapter);

                    articuloAdapter.setOnClickListener(new ArticuloAdapter.OnClickListener() {
                        @Override
                        public void onItemClick(View view, Product obj, int pos) {

                            //if (true){//!encontrarRepetido(obj.getCode())) {
                              etSearchArticulo.setText(obj.getDescription());
//                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.showSoftInputFromInputMethod(.getWindowToken(), 0);
                                rvArticulos.setVisibility(View.GONE);
                             callStockPrecio(obj);
//                            } else {
//                                rvArticulos.setVisibility(View.GONE);
//                                ToastSilicon.toastDangerOne(getActivity(),"Este articulo ya fue agregado!! Porfavor seleccione otro", Toast.LENGTH_SHORT);
//
//                            }

                        }

                        @Override
                        public void onItemLongClick(View view, Product obj, int pos) {

                        }
                    });


                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {


                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {


                }


            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {


            }
        });
    }


    private void callStockPrecio(Product obj) {
        ((ContenedorActivity) getActivity()).showProgress(true);
        Map<String, String> dataConsulta = new HashMap<>();
        dataConsulta.put("usuario",carritoRequestStatic.getUsuario());
        dataConsulta.put("codEmpresa",carritoRequestStatic.getCodEmpresa());
        dataConsulta.put("codSede",carritoRequestStatic.getCodSede());
        dataConsulta.put("codCanal",carritoRequestStatic.getCodCanal());
        dataConsulta.put("codCondicion",carritoRequestStatic.getCodCondicion());
        dataConsulta.put("codAlmacen",carritoRequestStatic.getCodAlmacen());
        dataConsulta.put("codItem",obj.getCode());
        dataConsulta.put("unidadMedida",obj.getUm());
        dataConsulta.put("codLista",carritoRequestStatic.getCodLista());

        Call<DisponiblePrecioResponse> callUpdateUser = ApiRetrofitShort.getInstance(BASE_URL).getProductService().getDisponiblePrecio(dataConsulta);
        callUpdateUser.enqueue(new Callback<DisponiblePrecioResponse>() {
            @Override
            public void onResponse(Call<DisponiblePrecioResponse> call, Response<DisponiblePrecioResponse> response) {
                StatusResponse statusResponse = response.body().getStatus();
                if (statusResponse.getStatusCode().equals(Constants.STATUS.SUCCESS)) {
                    tvDisponible.setText(response.body().getDisponible()+"");
                    tvPrecio.setText(response.body().getPrecioSugerido()+"");
                    productSelected=obj;
                    productSelected.setPriceBase(response.body().getPrecioBase());
                    productSelected.setPriceSugerido(response.body().getPrecioSugerido());

                    ((ContenedorActivity) getActivity()).showProgress(false);
                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.WARNING)) {


                } else if (statusResponse.getStatusCode().equals(Constants.STATUS.ERROR)) {


                }


            }

            @Override
            public void onFailure(Call<DisponiblePrecioResponse> call, Throwable t) {


            }
        });
    }

    public boolean encontrarRepetido(String codArticulo) {
        boolean band = false;
//        if (sessionUsuario.getPaqueteCarrito() == null) {
//            return band;
//        } else {
//            List<CarritoCompras> pedidos = sessionUsuario.getPaqueteCarrito().getListaCarrito();
//            for (int i = 0; i < pedidos.size(); i++) {
//                if (codArticulo.equals(pedidos.get(i).getArticulo().getCodItem())) {
//                    band = true;
//                    break;
//                }
//            }
           return band;
//        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setRequestCode(int request_code) {
        this.request_code = request_code;
    }

//    private void dialogDatePickerLight(final Button bt) {
//        Calendar cur_calender = Calendar.getInstance();
//        DatePickerDialog datePicker = DatePickerDialog.newInstance(
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
//                        Calendar calendar = Calendar.getInstance();
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, monthOfYear);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                        long date_ship_millis = calendar.getTimeInMillis();
//                        bt.setText(Tools.getFormattedDateEvent(date_ship_millis));
//                    }
//                },
//                cur_calender.get(Calendar.YEAR),
//                cur_calender.get(Calendar.MONTH),
//                cur_calender.get(Calendar.DAY_OF_MONTH)
//        );
//        //set dark light
//        datePicker.setThemeDark(false);
//        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
//        datePicker.setMinDate(cur_calender);
//        datePicker.show(getActivity().getFragmentManager(), "Datepickerdialog");
//    }
//
//    private void dialogTimePickerLight(final Button bt) {
//        Calendar cur_calender = Calendar.getInstance();
//        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
//            @Override
//            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                calendar.set(Calendar.MINUTE, minute);
//                calendar.set(Calendar.AM_PM, calendar.get(Calendar.AM_PM));
//                long time_millis = calendar.getTimeInMillis();
//                bt.setText(Tools.getFormattedTimeEvent(time_millis));
//            }
//        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
//        //set dark light
//        datePicker.setThemeDark(false);
//        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
//        datePicker.show(getActivity().getFragmentManager(), "Timepickerdialog");
//    }

    public interface CallbackResult {
        void sendResult(int requestCode, Object obj);
    }

    public interface CallbackAddCarrito {
        void updateRecycler(Carrito carrito);

    }

}