package com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.firstfragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GestureDetectorCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jaredrummler.materialspinner.MaterialSpinner
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.ui.home.homefragment.FeaturedListAdapter
import com.manohar.ecommerceshoppingportal.ui.home.homefragment.ProductsListAdapter
import com.manohar.ecommerceshoppingportal.network.ProductsService
import com.manohar.ecommerceshoppingportal.model.SharedX
import com.rm.rmswitch.RMSwitch
import kotlinx.android.synthetic.main.nested_first_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class NestedFirstFragment : Fragment() {

    companion object {
        fun newInstance() =
            NestedFirstFragment()
    }
    private var productsAdapter: ProductsListAdapter?=null
    var root:View?=null
    private var featuredAdapter: FeaturedListAdapter?=null
    var gestureDetectorCompat: GestureDetectorCompat?=null
    private var productsService: ProductsService?=null
    private lateinit var viewModel: NestedFirstViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.nested_first_fragment, container, false)


        root!!.countriesList.setHasFixedSize(true)


        viewModel =
            ViewModelProvider(this).get(NestedFirstViewModel::class.java)



        productsAdapter =
            ProductsListAdapter(
                arrayListOf(),
                requireActivity(),
                1
            )

        featuredAdapter =
            FeaturedListAdapter(
                arrayListOf(),
                requireActivity(),
                2
            )

        CoroutineScope(Dispatchers.IO).let {
            viewModel.refresh()
        }



        if ( SharedX.customPrefs(requireActivity(),"androidx").getBoolean("switch1st", false))
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
        root!!.item_featured1.setHasFixedSize(true)



        return root
    }

    override fun onStart() {
        super.onStart()




        root!!.switchview.addSwitchObserver(RMSwitch.RMSwitchObserver { switchView, isChecked ->
            if (isChecked) {
                SharedX.customPrefs(requireActivity(), "androidx").edit()
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
                SharedX.customPrefs(requireActivity(), "androidx").edit()
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


        root!!.item_featured1.apply {
            layoutManager = LinearLayoutManager(context)
            root!!.item_featured1.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
            adapter = featuredAdapter
        }




        CoroutineScope(Dispatchers.IO).let {
            observeViewModel()
        }


        val spinner: MaterialSpinner = root!!.mySpinner
        spinner.setItems(
            "Newest First",
            "Low to High Price",
            "High to Low Price"
        )
        spinner.setOnItemSelectedListener { view, position, id, item ->

            when(position)
            {
                0 -> {viewModel.refresh()
                    Snackbar.make(
                        view,
                        "Newest First",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                1 -> {viewModel.loadlowprice()
                    Snackbar.make(
                        view,
                        "Sorting by Low Price",
                        Snackbar.LENGTH_SHORT
                    ).show()}
                2 -> { viewModel.loadnewest()
                    Snackbar.make(
                        view,
                        "Sorting by High Price",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
            }




        }

    }


    fun observeViewModel() {

        //1st tab horizontal Recyclerview
        viewModel.products.observe(requireActivity(), Observer {products ->
            root!!.countriesList.visibility = View.VISIBLE

            products?.let { productsAdapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })

        //1st tab Featured Products
        viewModel.featproduct1.observe(requireActivity(), Observer {featproducts ->
            root!!.item_featured1.visibility = View.VISIBLE

            featproducts?.let { featuredAdapter!!.updateFeaturedItems(it) } //'it' refers to list of the countries
        })


        viewModel.loaderror.observe(requireActivity(), Observer {iserror->
            iserror?.let {  root!!.list_error.visibility = if (it) View.VISIBLE else View.GONE}
        })

        viewModel.isloading.observe(requireActivity(), Observer {isLoading ->
            isLoading?.let {  root!!.progressbar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    root!!.list_error.visibility = View.GONE
                    root!!.countriesList.visibility = View.GONE

                }
            }

        })






    }




}