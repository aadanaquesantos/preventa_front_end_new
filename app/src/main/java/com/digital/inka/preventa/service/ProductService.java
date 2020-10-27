package com.digital.inka.preventa.service;


import com.digital.inka.preventa.model.ProductListResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ProductService {

    @GET("bonifItem")
    Call<ProductListResponse> bonifItem(@QueryMap Map<String, String> parametros);

}
