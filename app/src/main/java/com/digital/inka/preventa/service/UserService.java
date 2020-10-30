package com.digital.inka.preventa.service;


import com.digital.inka.preventa.model.StatusResponse;
import com.digital.inka.preventa.model.SueldoResponse;
import com.digital.inka.preventa.model.User;
import com.digital.inka.preventa.model.UserResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

public interface UserService {

    @POST("login")
    Call<UserResponse> login(@Body User user);

    @GET("userByEmail")
    Call<UserResponse> userByEmail(@QueryMap Map<String, String> parametros);
    @PUT("updateUserLogin")
    Call<StatusResponse> updateUserLogin(@Body User user);


    @GET("getSueldo")
    Call<SueldoResponse> getSueldo(@QueryMap Map<String, String> parametros);
}
