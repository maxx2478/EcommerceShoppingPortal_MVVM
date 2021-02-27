package com.manohar.ecommerceshoppingportal.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.manohar.ecommerceshoppingportal.model.Blog
import com.manohar.ecommerceshoppingportal.model.Product
import com.manohar.ecommerceshoppingportal.ui.MasterClass
import io.reactivex.Single
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProductsService {

    private  val BASE_URL="https://practiceproject-b4085-default-rtdb.firebaseio.com/"
    private val api: ProductApiEndpoints

    val cacheSize = (5*1024*1024).toLong()
    val myCache = Cache(MasterClass.appContext.cacheDir, cacheSize)
    init {



        val okHttpClient = OkHttpClient.Builder()
            // Specify the cache we created earlier.
            .cache(myCache)
            // Add an Interceptor to the OkHttpClient.
            .addInterceptor { chain ->

                // Get the request from the chain.
                var request = chain.request()

                /*
                *  Leveraging the advantage of using Kotlin,
                *  we initialize the request and change its header depending on whether
                *  the device is connected to Internet or not.
                */
                request = if (hasNetwork(MasterClass.appContext)!!)
                /*
                *  If there is Internet, get the cache that was stored 5 seconds ago.
                *  If the cache is older than 5 seconds, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-age' attribute is responsible for this behavior.
                */
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                /*
                *  If there is no Internet, get the cache that was stored 7 days ago.
                *  If the cache is older than 7 days, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-stale' attribute is responsible for this behavior.
                *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                */
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                // End of if-else statement

                // Add the modified request to the chain.
                chain.proceed(request)
            }
            .build()

        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build().create(ProductApiEndpoints::class.java)


    }

    fun getProducts():Single<List<Product>>
    {
        return api.getProducts()
    }

    fun getfeatProducts():Single<List<Product>>
    {
        return api.getFeaturedProduct1()
    }

    fun getcartitems():Single<HashMap<String, Product>>
    {
        return api.getCartItems()
    }

    fun getblogitems():Single<HashMap<String, Blog>>
    {
        return api.getBlogItems()
    }

    fun getPlacedItems():Single<HashMap<String, Product>>
    {
        return api.getPlacedOrders()
    }


    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }



}