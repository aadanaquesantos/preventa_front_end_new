package com.digital.inka.preventa.service;


import com.digital.inka.preventa.model.DisponiblePrecioResponse;
import com.digital.inka.preventa.model.ProductListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ProductService {

    @GET("bonifItem")
    Call<ProductListResponse> bonifItem(@QueryMap Map<String, String> parametros);

    @GET("getSugeridos")
    Call<ProductListResponse> getSugeridos(@QueryMap Map<String, String> parametros);

    @GET("getArticulos")
    Call<ProductListResponse> getArticulos(@QueryMap Map<String, String> parametros);

    @GET("getDisponiblePrecio")
    Call<DisponiblePrecioResponse> getDisponiblePrecio(@QueryMap Map<String, String> parametros);
}
