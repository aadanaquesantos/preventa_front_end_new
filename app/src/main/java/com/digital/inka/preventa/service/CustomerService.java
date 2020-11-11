package com.digital.inka.preventa.service;



import com.digital.inka.preventa.model.CustomerInfoResponse;
import com.digital.inka.preventa.model.CustomerLocalListResponse;
import com.digital.inka.preventa.model.CustomerPedidoResponse;
import com.digital.inka.preventa.model.DispatchAddressResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface CustomerService {

    @GET("getClientesByDia")
    Call<CustomerLocalListResponse> getClientesByDia(@QueryMap Map<String, String> parametros);

    @GET("getCustomerInfo")
    Call<CustomerInfoResponse> getCustomerInfo(@QueryMap Map<String, String> parametros);
    @GET("getCustomerPedido")
    Call<CustomerPedidoResponse> getCustomerPedido(@QueryMap Map<String, String> parametros);

    @GET("getAddressPedido")
    Call<DispatchAddressResponse> getAddressPedido(@QueryMap Map<String, String> parametros);
}

