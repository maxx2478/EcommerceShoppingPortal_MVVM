package com.manohar.ecommerceshoppingportal.view

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.github.piasy.biv.view.BigImageView
import com.manohar.ecommerceshoppingportal.R
import com.manohar.ecommerceshoppingportal.model.CartProduct
import com.manohar.ecommerceshoppingportal.model.Product
import com.manohar.ecommerceshoppingportal.util.getProgressDrawable
import com.manohar.ecommerceshoppingportal.util.loadimage
import com.manohar.ecommerceshoppingportal.viewmodel.NestedThirdViewModel
import com.manohar.ecommerceshoppingportal.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_product_view.*
import mehdi.sakout.fancybuttons.FancyButton

class ProductView : AppCompatActivity() {

     lateinit  var productViewModel : ProductViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BigImageViewer.initialize(GlideImageLoader.with(this));

        setContentView(R.layout.activity_product_view)


        var bundle:Bundle = intent.extras!!
        var id = bundle.getInt("id", 0)
        var name = bundle.getString("name", "")

        var image = bundle.getString("image", "")
        var desc = bundle.getString("desc", "")
        var price = bundle.getInt("price", 0)



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
            productViewModel.addtocart(CartProduct(id, name, price, desc, image))
        })


    }
}