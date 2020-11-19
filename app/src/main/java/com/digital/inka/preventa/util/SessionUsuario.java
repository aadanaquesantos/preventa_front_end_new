package com.digital.inka.preventa.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.digital.inka.preventa.model.CarritoList;
import com.google.gson.Gson;


import java.util.ArrayList;


public class SessionUsuario {

    SharedPreferences preferences; //Lista de Preferencias
    SharedPreferences.Editor editor; // Editor de Preferencias
    Context _context; // Contexto
    int PRIVATE_MODE = 0; // Modo de Preferencias -> es el modo seguro solo la aplicacion accede a este archivo xml
    public static final String PREFER_NAME = "SESION_DIGITAL_INKA_PV";// Nombre de archivo de preferencias compartidas
    public static final String IS_USER_LOGIN = "isUserLoggedIn"; // Todas las claves de preferencias compartidas
    public static final String KEY_NAME = "nombre"; // Nombre de usuario (hacer pública la variable para acceder desde fuera)
    public static final String KEY_EMAIL = "email"; // Dirección de correo electrónico (hacer pública la variable para acceder desde fuera)
    Gson gson=new Gson();

    public SessionUsuario(Context context){
        this._context = context;
        preferences = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void IniciarSession(Boolean seinicio, String usuario, String codAplicacion){
        editor.putBoolean("login",seinicio);
        editor.putString("usuario",usuario);
         if(seinicio) {
            editor.putString("codigo", codAplicacion);
        }
        editor.commit();
    }

    public Boolean getSession(){
        return preferences.getBoolean("login",false);
    }

    public String getCodigoAplicacion(){
        String codigo = preferences.getString("codigo","");
        return codigo;
    }


    public void guardarUsuario(String username){
        editor.putString("usuario",username);
        editor.commit();
    }
    public void guardarProvider(String provider){
        editor.putString("provider",provider);
        editor.commit();
    }

    public String getUsuario(){
        return  preferences.getString("usuario","");
    }

    public void saveCarritoList(CarritoList carritoList){
        Gson gson = new Gson();
        String paqueteJson = gson.toJson(carritoList);
        editor.putString("carritoList",paqueteJson);
        editor.commit();
    }
    public CarritoList getCarritoList(){
        String json = preferences.getString("carritoList", "");
        CarritoList obj = gson.fromJson(json, CarritoList.class);
        return obj;
    }






}