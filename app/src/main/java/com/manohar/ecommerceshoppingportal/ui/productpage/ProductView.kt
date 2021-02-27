package com.manohar.ecommerceshoppingportal.ui.productpage

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.github.piasy.biv.view.BigImageView
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.CartProduct
import com.manohar.ecommerceshoppingportal.util.getProgressDrawable
import kotlinx.android.synthetic.main.activity_product_view.*
import mehdi.sakout.fancybuttons.FancyButton

class ProductView : AppCompatActivity() {

     lateinit  var productViewModel : ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BigImageViewer.initialize(GlideImageLoader.with(this));

        setContentView(R.layout.activity_product_view)


        val bundle:Bundle = intent.extras!!
        val id = bundle.getInt("id", 0)
        val name = bundle.getString("name", "")

        val image = bundle.getString("image", "")
        val desc = bundle.getString("desc", "")
        val price = bundle.getString("price", "0")



        val progressDrawable = getProgressDrawable(this)
        producttitle.setText(name)
        productprice.setText("$" + price.toString())
        productdesc.text = desc


        var imageViewer = findViewById<BigImageView>(R.id.productimage)
        val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher)


        imageViewer.showImage(Uri.parse(image))

        productViewModel =
            ViewModelProvider(this).get(ProductViewModel::class.java)

        var addtocartx = findViewById<FancyButton>(R.id.addtocart)
        addtocartx.setOnClickListener(View.OnClickListener {
            productViewModel.addtocart(CartProduct(id, name, price.trim().toInt(), desc, image))
        })


    }
}