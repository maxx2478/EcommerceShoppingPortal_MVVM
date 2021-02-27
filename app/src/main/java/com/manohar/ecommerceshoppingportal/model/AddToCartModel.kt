package com.manohar.ecommerceshoppingportal.model

import com.google.gson.annotations.SerializedName

data class CartProduct(val id: Int,  val name:String, val price:Int, val desc:String, val image:String)
{

}