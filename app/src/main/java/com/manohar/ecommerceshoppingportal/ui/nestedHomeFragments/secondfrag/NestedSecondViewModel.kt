package com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.secondfrag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manohar.ecommerceshoppingportal.model.Product
import com.manohar.ecommerceshoppingportal.network.ProductsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class NestedSecondViewModel : ViewModel() {

    val products = MutableLiveData<List<Product>>()
    val featproduct1 = MutableLiveData<List<Product>>()

    val loaderror = MutableLiveData<Boolean>()
    val isloading = MutableLiveData<Boolean>()
    private val productsService = ProductsService()
    private val disposable = CompositeDisposable()
    private val disposable2 = CompositeDisposable()


    fun refresh() {
        loaddata()

    }



    private fun loaddata() {

        isloading.value = true

        disposable.add(
            productsService.getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        var sortedvalue = value!!.sortedByDescending { it.id }
                        products.value = sortedvalue
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loaderror.value = true
                        isloading.value = false;
                    }
                })

        )

        disposable2.add(
            productsService.getfeatProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        featproduct1.value = value
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loaderror.value = true
                        isloading.value = false;
                    }
                })

        )




    }

    fun loadnewest()
    {
        loadnewests()
    }

    fun loadlowprice()
    {
        loadlowfirst()
    }

    private fun loadlowfirst() {

        isloading.value = true

        disposable.add(
            productsService.getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        var sortedvalue = value!!.sortedBy { it.price }
                        products.value = sortedvalue
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loaderror.value = true
                        isloading.value = false;
                    }
                })

        )

        disposable2.add(
            productsService.getfeatProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        featproduct1.value = value
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loaderror.value = true
                        isloading.value = false;
                    }
                })

        )




    }

    private fun loadnewests() {

        isloading.value = true

        disposable.add(
            productsService.getProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        var sortedvalue = value!!.sortedByDescending { it.price }
                        products.value = sortedvalue
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loaderror.value = true
                        isloading.value = false;
                    }
                })

        )

        disposable2.add(
            productsService.getfeatProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Product>>() {
                    override fun onSuccess(value: List<Product>?) {
                        featproduct1.value = value
                        isloading.value = false
                        loaderror.value = false
                    }

                    override fun onError(e: Throwable?) {
                        loaderror.value = true
                        isloading.value = false;
                    }
                })

        )




    }


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
        disposable2.clear()
    }


}