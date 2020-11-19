package com.digital.inka.preventa.model

import java.util.*
import kotlin.collections.ArrayList


data class Product(
        var code:String,
        var description:String,
        var uri:String,
        var priceBase:Double,
        var priceSugerido:Double,
        var um:String,
        var isBonif:Boolean,
        var isSugerido:Boolean

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
    var dni:String,
    var ruc:String,
    var email:String,
    var phone:String,
    var cellPhone:String,
    var address:String,
    var status:String
)
data class DivisionEmpresa(
        var code:String,
        var description:String
)
data class DispatchAddress(
        var code:String,
        var description:String,
        var route:Route,
        var statusLocal:String,
        var listaPrecios:ListaPrecios
)
data class DispatchAddressResponse(
        var address:DispatchAddress,
        var status:StatusResponse
)
data class Route(
        var code:String,
        var description:String,
        var division:DivisionEmpresa ,
        var company:Company
)

//todo ok
data class CustomerInfoResponse(
        var customer:Customer,
        var addresses:ArrayList<DispatchAddress>? = null,
        var status:StatusResponse
)
// todo ok
data class CustomerPedidoResponse(
        var customer:Customer,
        var dispatchAddress: DispatchAddress,
        var almacenes: ArrayList<Almacen>,
        var condiciones: ArrayList<CondicionPago>,
        var tipoDocs: ArrayList<TipoDoc>,
        var status:StatusResponse
)

data class RouteListResponse(
        var routes:ArrayList<Route>?= null,
        var status:StatusResponse

)
data class Company(
        var code:String,
        var description:String
)
data class CustomerLocalListResponse(
        var status:StatusResponse,
        var customerLocals: ArrayList<CustomerLocal>? = null
)
data class  CustomerLocal(
        var customer: Customer,
        var dispatchAddress: DispatchAddress
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
    var statusCode: Integer ,
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

data class CondicionPago(
        val code: String? = null,
        val description: String
)

data class Almacen(
        val code: String,
        val description: String,
        val codLocalidad:String,
        val codMesa:String,
        val codEmpresa:String,
        val codSede:String,
        val codCanal:String,
        val codVendedor:String
)
data class  DisponiblePrecioResponse(
        val disponible:Double,
        val precioBase:Double,
        val precioSugerido:Double,
        val status: StatusResponse
)

data class AlmacenListResponse(
       val almacenes:ArrayList<Almacen>,
       var status:StatusResponse
)
data class CondicionListResponse(
        var condiciones:ArrayList<CondicionPago>,
        var status:StatusResponse
)
data class TipoDocListResponse(
        var tipoDocs:ArrayList<TipoDoc>,
        var status:StatusResponse
)
data class TipoDoc(
        var code:String,
        var description:String
)

data class Vendedor(
        val code:String,
        var description:String
)

data class ListaPrecios(
        val code:String,
        var description:String
)

data class Pedido(
        var usuario:String,
        var codEmpresa:String,
        var codSede:String,
        var codAlmacen:String,
        var codLocalidad:String,
        var codMesa:String,
        var codVendedor:String,
        var codCondicion:String,
        var codListaPrecios: String,
        var codLocal:String,
        var codTipoDoc:String,
        var codCanal:String,
        var codRuta:String,
        var codCliente:String,
        var totalImporte:Double,
        var totalDescuentos:Double,
        var totalIgv:Double,
        var totalPercepcion:Double,
        var totalFlete:Double,
        var detallePedidos:List<DetallePedido>
)

  data class  DetallePedido(
         var product:Product,
         var importeTotal:Double,
         var importeIgv:Double,
         var importePercepcion:Double,
         var importeDescuentos:Double,
         var cantidad:Double
  )
data class PedidoResponse(
        var pedido:Pedido,
        var status: StatusResponse
)





