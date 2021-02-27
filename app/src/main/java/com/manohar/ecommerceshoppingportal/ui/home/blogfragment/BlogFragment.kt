package com.manohar.ecommerceshoppingportal.ui.home.blogfragment

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
import kotlinx.android.synthetic.main.blog_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class blogFragment : Fragment() {

    companion object {
        fun newInstance() =
            blogFragment()
    }
    private var placedordersadapter: BlogListAdapter?=null
    var root:View?=null
    private lateinit var viewModel: BlogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root=  inflater.inflate(R.layout.blog_fragment, container, false)


        root!!.blogitemsrv.setHasFixedSize(true)

        return root
    }


    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(this).get(BlogViewModel::class.java)

        CoroutineScope(Dispatchers.IO).let {

            observeViewModel()
            viewModel.refresh()
        }

        placedordersadapter =
                BlogListAdapter(
                        arrayListOf(),
                        requireActivity()
                )

        root!!.blogitemsrv.apply {
            layoutManager = LinearLayoutManager(context)
            root!!.blogitemsrv.layoutManager = LinearLayoutManager(requireActivity(),
                    RecyclerView.VERTICAL,
                    false)
            root!!.blogitemsrv.adapter = placedordersadapter
        }

    }


    fun observeViewModel() {

        viewModel.products.observe(requireActivity(), Observer { products ->
            root!!.blogitemsrv.visibility = View.VISIBLE

            products?.let { placedordersadapter!!.updateCountryList(it) } //'it' refers to list of the countries
        })


    }


}