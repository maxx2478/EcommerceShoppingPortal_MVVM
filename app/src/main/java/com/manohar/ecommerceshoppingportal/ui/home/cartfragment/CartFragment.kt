package com.manohar.ecommerceshoppingportal.ui.home.cartfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.ui.home.placedorderfragment.PlacedOrderListAdapter
import kotlinx.android.synthetic.main.cart_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }
    private var placedordersadapter: PlacedOrderListAdapter?=null
    private lateinit var viewModel: CartViewModel
    var root:View?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root =  inflater.inflate(R.layout.cart_fragment, container, false)


        root!!.cartlistrv.setHasFixedSize(true)

        return root!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.IO).let {

            viewModel.refresh()
        }

        placedordersadapter =
                PlacedOrderListAdapter(
                        arrayListOf(),
                        requireActivity()
                )

        root!!.cartlistrv.apply {
            layoutManager = LinearLayoutManager(context)
            root!!.cartlistrv.layoutManager = LinearLayoutManager(requireActivity(),
                    RecyclerView.VERTICAL,
                    false)
            root!!.cartlistrv.adapter = placedordersadapter
        }

        CoroutineScope(Dispatchers.IO).let {

            observeViewModel()

        }

    }

    fun observeViewModel() {









        viewModel.products.observe(requireActivity(), Observer { products ->
            root!!.cartlistrv.visibility = View.VISIBLE

            products?.let { placedordersadapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })



    }
}