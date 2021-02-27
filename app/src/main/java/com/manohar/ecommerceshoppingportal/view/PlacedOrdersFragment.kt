package com.manohar.ecommerceshoppingportal.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.PlcaedOrderListAdapter
import com.manohar.ecommerceshoppingportal.model.Product
import com.manohar.ecommerceshoppingportal.model.ProductsService
import com.manohar.ecommerceshoppingportal.viewmodel.MasterClass
import com.manohar.ecommerceshoppingportal.viewmodel.PlacedOrdersViewModel
import kotlinx.android.synthetic.main.blog_fragment.view.*
import kotlinx.android.synthetic.main.placed_orders_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class PlacedOrdersFragment : Fragment() {

    private var placedordersadapter: PlcaedOrderListAdapter?=null
    var root:View?=null
    private lateinit var viewModel: PlacedOrdersViewModel
    var productsService = ProductsService()
    companion object {
        fun newInstance() =
            PlacedOrdersFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        root=  inflater.inflate(R.layout.placed_orders_fragment, container, false)



        root!!.placedordersrv.setHasFixedSize(true)


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(this).get(PlacedOrdersViewModel::class.java)



        CoroutineScope(Dispatchers.IO).let {

            viewModel.refresh()
        }

        placedordersadapter =
                PlcaedOrderListAdapter(
                        arrayListOf(),
                        requireActivity()
                )

        root!!.placedordersrv.apply {
            layoutManager = LinearLayoutManager(context)
            root!!.placedordersrv.layoutManager = LinearLayoutManager(requireActivity(),
                    RecyclerView.VERTICAL,
                    false)
            root!!.placedordersrv.adapter = placedordersadapter
        }

        CoroutineScope(Dispatchers.IO).let {

            observeViewModel()

        }

    }

    fun observeViewModel() {









        viewModel.products.observe(requireActivity(), Observer { products ->
            root!!.placedordersrv.visibility = View.VISIBLE

            products?.let { placedordersadapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })



    }




}