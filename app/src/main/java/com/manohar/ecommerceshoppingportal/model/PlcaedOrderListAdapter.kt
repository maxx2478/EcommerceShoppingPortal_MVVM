package com.manohar.ecommerceshoppingportal.model

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.util.getProgressDrawable
import com.manohar.ecommerceshoppingportal.util.loadimage
import com.manohar.ecommerceshoppingportal.view.ProductView
import kotlinx.android.synthetic.main.item_placedorders.view.*
import org.w3c.dom.Text
import kotlin.concurrent.thread

class PlcaedOrderListAdapter:
    RecyclerView.Adapter<PlcaedOrderListAdapter.CountryViewHolder>
{
    var context:Context?=null
    var products:ArrayList<Product>?=null
    constructor(products:ArrayList<Product>, context:Context): super()
    {
        this.context = context
        this.products = products
    }

    fun updateCountryList(newCountries: List<Product>)
    {
        products!!.clear()
        products!!.addAll(newCountries)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.item_placedorders, parent, false)
        )

    override fun getItemCount() = products!!.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(products!![position])

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ProductView::class.java)
            intent.putExtra("id", products!!.get(position)!!.id)
            intent.putExtra("name", products!!.get(position)!!.name)
            intent.putExtra("price", products!!.get(position)!!.price.toString())
            intent.putExtra("desc", products!!.get(position)!!.description)
            intent.putExtra("image", products!![position]!!.image)

            context!!.startActivity(intent)
        })
    }

    class CountryViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val name = view.name
        val image = view.image

        private val progressDrawable = getProgressDrawable(view.context)
        fun bind(product:Product)
        {
            name.text = product.name
            // price.text = "$" + product.price.toString()
            image.loadimage(product.image, progressDrawable)
        }

    }



}