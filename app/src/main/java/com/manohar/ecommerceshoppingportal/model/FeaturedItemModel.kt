package com.manohar.ecommerceshoppingportal.model

import android.accounts.AuthenticatorDescription
import com.google.firebase.database.Exclude
import com.google.gson.annotations.SerializedName

class FeaturedItemModel
{
    var id: Int? = null
    var name: String?= null
    var price: Int?= null
    var description: String?= null
    var image: String?= null

    constructor()
    {}


    constructor(id:Int, name:String, price:Int, description:String, image:String)
    {
        this.id = id.toInt()
        this.name = name
        this.price = price
        this.description = description
        this.image = image
    }



}