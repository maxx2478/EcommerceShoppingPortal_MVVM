package com.manohar.ecommerceshoppingportal.ui.home.placedorderfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manohar.ecommerceshoppingportal.model.Product
import com.manohar.ecommerceshoppingportal.network.ProductsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PlacedOrdersViewModel : ViewModel() {

    //val products = MutableLiveData<HashMap<String,Product>>()
    val products = MutableLiveData<List<Product>>()

    private val productsService = ProductsService()
    private val disposable = CompositeDisposable()


    fun refresh() {
        loaddata()

    }



    private fun loaddata() {



        disposable.add(
            productsService.getcartitems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<HashMap<String, Product>>() {
                    override fun onSuccess(value: HashMap<String, Product>?) {
                        val listarray = ArrayList<Product>(value!!.values)
                        products.value = listarray

                    }

                    override fun onError(e: Throwable?) {

                    }
                })

        )


    }



    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}