package com.manohar.ecommerceshoppingportal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.manohar.ecommerceshoppingportal.model.CartProduct
import com.manohar.ecommerceshoppingportal.model.Product

class ProductViewModel: ViewModel()
{
    private lateinit var database: DatabaseReference

    var int:Long = 1
    fun addtocart(product: CartProduct)
    {
        database = FirebaseDatabase.getInstance().getReference("cart")
        database.push().setValue(product)

    }


}