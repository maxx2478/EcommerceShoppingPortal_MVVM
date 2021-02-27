package com.manohar.ecommerceshoppingportal.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.manohar.ecommerceshoppingportal.model.Blog
import com.manohar.ecommerceshoppingportal.model.ProductsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class BlogViewModel : ViewModel() {


    val products = MutableLiveData<List<Blog>>()
    //val products = MutableLiveData<HashMap<String, Blog>>()


    private val productsService = ProductsService()
    private val disposable = CompositeDisposable()


    fun refresh() {
        loaddata()

    }



    private fun loaddata() {



        disposable.add(
                productsService.getblogitems()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<HashMap<String, Blog>>() {
                            override fun onSuccess(value: HashMap<String, Blog>?) {
                                val listarray = ArrayList<Blog>(value!!.values)
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