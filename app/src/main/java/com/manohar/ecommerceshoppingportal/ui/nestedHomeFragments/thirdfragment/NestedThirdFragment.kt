package com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.thirdfragment

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
import com.manohar.ecommerceshoppingportal.ui.home.homefragment.ProductsListAdapter
import com.manohar.ecommerceshoppingportal.model.SharedX
import com.manohar.ecommerceshoppingportal.ui.nestedHomeFragments.firstfragment.NestedFirstViewModel
import com.rm.rmswitch.RMSwitch
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class NestedThirdFragment : Fragment() {

    private lateinit var galleryViewModel: NestedFirstViewModel
    private var productsAdapter: ProductsListAdapter?=null
    var root:View?=null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

         root = inflater.inflate(R.layout.fragment_gallery, container, false)

        root!!.countriesList.setHasFixedSize(true)


        return root
    }


    override fun onStart() {
        super.onStart()

        galleryViewModel =
                ViewModelProvider(this).get(NestedFirstViewModel::class.java)





        CoroutineScope(Dispatchers.IO).let {

            galleryViewModel.loadnewest()
        }

        productsAdapter =
                ProductsListAdapter(
                        arrayListOf(),
                        requireActivity(),
                        1
                )


        if ( SharedX.customPrefs(requireActivity(),"androidx2").getBoolean("switch1st", false))
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
                            2
                    )
            root!!.countriesList.apply {
                layoutManager = LinearLayoutManager(context)
                root!!.countriesList.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
                adapter = productsAdapter
            }
            CoroutineScope(Dispatchers.IO).let {

                observeViewModel()
            }
        }



        root!!.switchview.addSwitchObserver(RMSwitch.RMSwitchObserver { switchView, isChecked ->
            if (isChecked) {
                SharedX.customPrefs(requireActivity(), "androidx2").edit()
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
                SharedX.customPrefs(requireActivity(), "androidx2").edit()
                        .putBoolean("switch1st", false).apply()

                productsAdapter =
                        ProductsListAdapter(
                                arrayListOf(),
                                requireActivity(),
                                2
                        )
                root!!.countriesList.apply {
                    layoutManager = LinearLayoutManager(context)
                    root!!.countriesList.layoutManager =
                            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
                    adapter = productsAdapter
                }
                CoroutineScope(Dispatchers.IO).let {

                    observeViewModel()
                }
            }
        })




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
                0 -> {galleryViewModel.refresh()
                    Snackbar.make(
                            view,
                            "Newest First $position",
                            Snackbar.LENGTH_SHORT
                    ).show()
                }
                1 -> {galleryViewModel.loadlowprice()
                    Snackbar.make(
                            view,
                            "Sorting by Low Price $position",
                            Snackbar.LENGTH_SHORT
                    ).show()}
                2 -> { galleryViewModel.loadnewest()
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
        galleryViewModel.products.observe(requireActivity(), Observer {products ->
            root!!.countriesList.visibility = View.VISIBLE

            products?.let { productsAdapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })




        galleryViewModel.loaderror.observe(requireActivity(), Observer {iserror->
            iserror?.let {  root!!.list_error.visibility = if (it) View.VISIBLE else View.GONE}
        })

        galleryViewModel.isloading.observe(requireActivity(), Observer {isLoading ->
            isLoading?.let {  root!!.progressbar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    root!!.list_error.visibility = View.GONE
                    root!!.countriesList.visibility = View.GONE

                }
            }

        })


    }



}