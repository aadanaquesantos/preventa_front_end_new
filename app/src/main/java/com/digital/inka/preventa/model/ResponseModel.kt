package com.digital.inka.preventa.model

import java.util.*


data class Product(
        var code:String,
        var description:String,
        var uri:String,
        var price:Double,
        var isBonif:Boolean

)
data class ProductListResponse(
    var status:StatusResponse,
    var products: ArrayList<Product>? = null

)

data class User(
        var userEmail:String,
        var fullName:String,
        var password:String,
        var username:String,
        var email:String,
        var dni:String,
        var celular:String
)
data class UserResponse(
    var status:StatusResponse,
    var user: User? = null
)

data class Customer(
    var code:String,
    var description:String,
    var address:String,
    var status:String
)
data class CustomerListResponse(
        var status:StatusResponse,
        var customers: ArrayList<Customer>? = null
)


data class MenuDashboard(
        var codMenu:String,
        var nombre: String,
        var descripcion: String,
        var moneda: String ,
        var precio:Double,
        var imagen: Int
)






data class StatusResponse(
    var statusCode: String = "",
    var statusText: String = ""
  )






data class LoginResponse(
    val user_id: String? = null,
    val token: String,
    val user_display_name: String,
    val user_email: String,
    val user_nicename: String,
    val message: String,
    val user_role: List<String>? = null,
    val avatar: String,
    val profile_image: String
)