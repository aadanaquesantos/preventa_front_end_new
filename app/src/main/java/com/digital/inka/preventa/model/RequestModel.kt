package com.digital.inka.preventa.model

import java.util.*
import kotlin.collections.ArrayList


data class UserRequest(
        var userEmail:String

)

data class CarritoRequest(
        var id:String,
        var usuario:String,
        var codEmpresa:String,
        var codSede:String,
        var codAlmacen:String,
        var codLocalidad:String,
        var codMesa:String,
        var codVendedor:String,
        var codCondicion:String,
         var codLocal:String,
        var codTipoDoc:String,
        var codCanal:String,
        var codRuta:String,
        var codLista:String,
        var codCliente:String,
        var carritoList:List<Carrito>

){
    constructor() : this("","",
            "", "", "","",
            "", "", "",
            "", "","","","","", emptyList()
    )
}
data class CarritoList(
        var listCarrito:ArrayList<Carrito>
){constructor() : this(ArrayList<Carrito>())}

data class Carrito(
        var product:Product,
        var cantidad:Int,
        var importe:Double
)



