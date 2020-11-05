package com.digital.inka.preventa.model

import java.util.*
import kotlin.collections.ArrayList


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

data class SueldoResponse(
        var sueldo:Double,
        var cuota:Double,
        var avanceCuota:Double,
        var status:StatusResponse
)


data class AvanceResponse(
        var totalDias:Int,
        var restoDias:Int,
        var avanceDias:Int,
        var linealidad:Double,
        var necesidadDiaria:Double,
        var clientesAtendidos:Int,
        var proyeccion:Double,
        var status:StatusResponse
)

data class AvanceProveedor(
        var codProveedor:String,
        var descProveedor:String,
        var avance:Double
)

data class AvanceProveedorListResponse(
        var status:StatusResponse,
        var avances: ArrayList<AvanceProveedor>? = null
)


data class AvancePoliticaListResponse(
        var status:StatusResponse,
        var avances: ArrayList<AvancePolitica>? = null
)
data class  AvancePolitica(
        var codPolitica:String,
        var descPolitica:String,
        var montoPolitica:Double

)


data class PeriodoListResponse(
        var status:StatusResponse,
        var periodos:ArrayList<Periodo>?=null

)
data class Periodo(
        var annio:String,
        var description:String,
        var startDate:String,
        var endDate:String
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