package com.manohar.ecommerceshoppingportal.ui.home.homefragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.Product
import com.manohar.ecommerceshoppingportal.util.getProgressDrawable
import com.manohar.ecommerceshoppingportal.util.loadimage
import com.manohar.ecommerceshoppingportal.ui.productpage.ProductView

class FeaturedListAdapter:
    RecyclerView.Adapter<FeaturedListAdapter.FeaturedViewHolder>
{
    var context:Context?=null
    var featuredproducts:ArrayList<Product>?=null
    var id:Int?=null
    constructor(products:ArrayList<Product>, context:Context, layout:Int): super()
    {
        this.context = context
        this.featuredproducts = products
        this.id = layout
    }

    fun updateFeaturedItems(featuredproduct: List<Product>)
    {

        featuredproducts!!.clear()
        featuredproducts!!.addAll(featuredproduct)
        notifyDataSetChanged()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =

        FeaturedViewHolder(
            LayoutInflater.from(parent.context)!!.inflate(R.layout.item_featured_home, parent, false)
        )




    override fun getItemCount() = featuredproducts!!.size

    override fun onBindViewHolder(holder: FeaturedViewHolder, position: Int) {
        holder.bind(featuredproducts!![position])

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, ProductView::class.java)
            intent.putExtra("id", featuredproducts!!.get(position).id)
            intent.putExtra("name", featuredproducts!!.get(position).name)
            intent.putExtra("price", featuredproducts!!.get(position).price.toString())
            intent.putExtra("desc", featuredproducts!!.get(position).description)
            intent.putExtra("image", featuredproducts!![position].image)

            context!!.startActivity(intent)
        })
    }

    class FeaturedViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val name = view.findViewById<TextView>(R.id.featname1)
        val image = view.findViewById<ImageView>(R.id.featimage1)
        val price = view.findViewById<TextView>(R.id.featprice1)
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(product: Product)
        {
            name.text = product.name
            price.text = "$" + product.price.toString()
            image.loadimage(product.image, progressDrawable)
        }

    }



}