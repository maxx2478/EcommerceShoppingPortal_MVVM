package com.manohar.ecommerceshoppingportal.network

import com.manohar.ecommerceshoppingportal.model.Blog
import com.manohar.ecommerceshoppingportal.model.Product
import io.reactivex.Single
import retrofit2.http.GET
import java.util.HashMap

interface ProductApiEndpoints
{
    @GET("products.json")
    fun getProducts(): Single<List<Product>>

    @GET("dealTabFeaturedItem.json")
    fun getFeaturedProduct1(): Single<List<Product>>

    @GET("cart.json")
    fun getCartItems(): Single<HashMap<String, Product>>

    @GET("placed.json")
    fun getPlacedOrders(): Single<HashMap<String, Product>>

    @GET("blog.json")
    fun getBlogItems(): Single<HashMap<String, Blog>>



}