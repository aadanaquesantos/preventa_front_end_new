package com.digital.inka.preventa.service;


import com.digital.inka.preventa.model.CarritoRequest;
import com.digital.inka.preventa.model.DisponiblePrecioResponse;
import com.digital.inka.preventa.model.PedidoResponse;
import com.digital.inka.preventa.model.ProductListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface PedidoService {

    @POST("saveCarrito")
    Call<PedidoResponse> saveCarrito(@Body CarritoRequest carritoRequest);


}
