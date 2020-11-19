package com.digital.inka.preventa.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digital.inka.R;
import com.digital.inka.preventa.activity.ContenedorActivity;
import com.digital.inka.preventa.adapter.ArticuloAdapter;
import com.digital.inka.preventa.adapter.CarritoAdapter;
import com.digital.inka.preventa.adapter.DetallePedidoAdapter;
import com.digital.inka.preventa.adapter.SugeridoAdapter;
import com.digital.inka.preventa.api.ApiRetrofitShort;
import com.digital.inka.preventa.model.Carrito;
import com.digital.inka.preventa.model.CarritoList;
import com.digital.inka.preventa.model.CarritoRequest;
import com.digital.inka.preventa.model.Constants;
import com.digital.inka.preventa.model.DisponiblePrecioResponse;
import com.digital.inka.preventa.model.Pedido;
import com.digital.inka.preventa.model.Product;
import com.digital.inka.preventa.model.ProductListResponse;
import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.util.SessionUsuario;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.digital.inka.preventa.model.Constants.Config.BASE_URL;


public class DialogPrevisualizarFragment extends DialogFragment {


    private static Pedido pedidoStatic;
    private View root_view;

    private MaterialButton btnArticulos;
    private MaterialButton btnPrevisualizar;
    private RecyclerView rvDetallePedidoPrev;
    private DetallePedidoAdapter detallePedidoAdapter;
    private TextView tvFleteReparto;
    private TextView tvVentaNeta;

    public static DialogPrevisualizarFragment newInstance(Pedido pedido) {
        DialogPrevisualizarFragment fragment = new DialogPrevisualizarFragment();
         pedidoStatic=pedido;
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionUsuario sessionUsuario=new SessionUsuario(getContext());
        root_view = inflater.inflate(R.layout.dialog_previsualizar_fragment, container, false);
        rvDetallePedidoPrev=root_view.findViewById(R.id.rvDetallePedidoPrev);
        tvFleteReparto=root_view.findViewById(R.id.tvFleteReparto);
        tvVentaNeta=root_view.findViewById(R.id.tvVentaNeta);

        rvDetallePedidoPrev.setHasFixedSize(true);
        rvDetallePedidoPrev.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        detallePedidoAdapter=new DetallePedidoAdapter(getContext(),pedidoStatic.getDetallePedidos(),getActivity());
        rvDetallePedidoPrev.setAdapter(detallePedidoAdapter);
        tvFleteReparto.setText("S/."+pedidoStatic.getTotalFlete());
        tvVentaNeta.setText("S/."+pedidoStatic.getTotalImporte());
         return root_view;
    }




    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }




    public interface CallbackResult {
        void sendResult(int requestCode, Object obj);
    }

    public interface CallbackAddCarrito {
        void updateRecycler(Carrito carrito);

    }

}