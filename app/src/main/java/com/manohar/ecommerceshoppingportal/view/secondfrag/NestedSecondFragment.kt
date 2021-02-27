package com.manohar.ecommerceshoppingportal.view.secondfrag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.ProductsListAdapter
import com.manohar.ecommerceshoppingportal.model.SharedX
import com.manohar.ecommerceshoppingportal.viewmodel.NestedFirstViewModel
import com.manohar.ecommerceshoppingportal.viewmodel.NestedSecondViewModel
import com.rm.rmswitch.RMSwitch
import kotlinx.android.synthetic.main.fragment_slideshow.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class NestedSecondFragment : Fragment() {

    private lateinit var nestedSecondViewModel: NestedFirstViewModel
    private var productsAdapter: ProductsListAdapter?=null
    var root:View?=null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_slideshow, container, false)






        return root
    }


    override fun onStart() {
        super.onStart()
        nestedSecondViewModel =
                ViewModelProvider(this).get(NestedFirstViewModel::class.java)
        root!!.countriesList.setHasFixedSize(true)


        CoroutineScope(Dispatchers.IO).let {

            nestedSecondViewModel.loadlowprice()

        }

        productsAdapter =
                ProductsListAdapter(
                        arrayListOf(),
                        requireActivity(),
                        1
                )


        if ( SharedX.customPrefs(requireActivity(),"androidx3").getBoolean("switch1st", false))
        {
            root!!.switchview.isChecked = true
            productsAdapter =
                    ProductsListAdapter(
                            arrayListOf(),
                            requireActivity(),
                            2
                    )
            root!!.countriesList.apply {
                root!!.countriesList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = productsAdapter
            }
            CoroutineScope(Dispatchers.IO).let {

                observeViewModel()
            }


        }
        else
        {
            root!!.switchview.isChecked = false
            productsAdapter =
                    ProductsListAdapter(
                            arrayListOf(),
                            requireActivity(),
                            1
                    )
            root!!.countriesList.apply {
                layoutManager = LinearLayoutManager(context)
                root!!.countriesList.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                adapter = productsAdapter
            }
            CoroutineScope(Dispatchers.IO).let {

                observeViewModel()
            }
        }



        root!!.switchview.addSwitchObserver(RMSwitch.RMSwitchObserver { switchView, isChecked ->
            if (isChecked) {
                SharedX.customPrefs(requireActivity(), "androidx3").edit()
                        .putBoolean("switch1st", true).apply()
                productsAdapter =
                        ProductsListAdapter(
                                arrayListOf(),
                                requireActivity(),
                                2
                        )
                root!!.countriesList.apply {
                    root!!.countriesList.layoutManager =
                            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    adapter = productsAdapter
                }
                CoroutineScope(Dispatchers.IO).let {

                    observeViewModel()
                }
            } else {
                SharedX.customPrefs(requireActivity(), "androidx3").edit()
                        .putBoolean("switch1st", false).apply()

                productsAdapter =
                        ProductsListAdapter(
                                arrayListOf(),
                                requireActivity(),
                                1
                        )
                root!!.countriesList.apply {
                    layoutManager = LinearLayoutManager(context)
                    root!!.countriesList.layoutManager =
                            LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
                    adapter = productsAdapter
                }
                CoroutineScope(Dispatchers.IO).let {

                    observeViewModel()
                }
            }
        })


        /*
        root!!.countriesList.apply {
            layoutManager = LinearLayoutManager(context)
            root!!.countriesList.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            adapter = productsAdapter
        }

         */



        observeViewModel()

        val spinner: MaterialSpinner = root!!.mySpinner
        spinner.setItems(
                "Newest First",
                "Low to High Price",
                "High to Low Price"
        )
        spinner.setOnItemSelectedListener { view, position, id, item ->

            when(position)
            {
                0 -> {nestedSecondViewModel.refresh()
                    Snackbar.make(
                            view,
                            "Newest First $position",
                            Snackbar.LENGTH_SHORT
                    ).show()
                }
                1 -> {nestedSecondViewModel.loadlowprice()
                    Snackbar.make(
                            view,
                            "Sorting by Low Price $position",
                            Snackbar.LENGTH_SHORT
                    ).show()}
                2 -> { nestedSecondViewModel.loadnewest()
                    Snackbar.make(
                            view,
                            "Sorting by High Price $position",
                            Snackbar.LENGTH_SHORT
                    ).show()

                }
            }




        }


    }


    fun observeViewModel() {

        //1st tab horizontal Recyclerview
        nestedSecondViewModel.products.observe(requireActivity(), Observer { products ->
            root!!.countriesList.visibility = View.VISIBLE

            products?.let { productsAdapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })




        nestedSecondViewModel.loaderror.observe(requireActivity(), Observer { iserror->
            iserror?.let {  root!!.list_error.visibility = if (it) View.VISIBLE else View.GONE}
        })

        nestedSecondViewModel.isloading.observe(requireActivity(), Observer { isLoading ->
            isLoading?.let {  root!!.progressbar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    root!!.list_error.visibility = View.GONE
                    root!!.countriesList.visibility = View.GONE

                }
            }

        })



    }



}