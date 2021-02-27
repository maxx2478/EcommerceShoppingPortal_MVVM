package com.manohar.ecommerceshoppingportal.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Long?,
    @SerializedName("desc")
    val description: String?,
    @SerializedName("image")
    val image: String?
)