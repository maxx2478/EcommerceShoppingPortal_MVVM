package com.manohar.ecommerceshoppingportal.ui.home.blogfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.Blog
import com.manohar.ecommerceshoppingportal.util.getProgressDrawable
import com.manohar.ecommerceshoppingportal.util.loadimage
import kotlinx.android.synthetic.main.item_blogs.view.*

class BlogListAdapter:
    RecyclerView.Adapter<BlogListAdapter.CountryViewHolder>
{
    var context:Context?=null
    var products:ArrayList<Blog>?=null
    constructor(products:ArrayList<Blog>, context:Context): super()
    {
        this.context = context
        this.products = products

    }

    fun updateCountryList(newCountries: List<Blog>)
    {
        products!!.clear()
        products!!.addAll(newCountries)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.item_blogs, parent, false)
        )



    override fun getItemCount() = products!!.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(products!![position])

        holder.itemView.setOnClickListener(View.OnClickListener {

        })
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val name = view.blogtitle
        val image = view.blogimage
        //val price = view.findViewById<TextView>(R.id.price)
        //val price = view.findViewById<TextView>(R.id.price)

        private val progressDrawable = getProgressDrawable(view.context)


        fun bind(product: Blog)
        {
            name.text = product.title
            // price.text = "$" + product.price.toString()
            image.loadimage(product.image, progressDrawable)
        }

    }



}