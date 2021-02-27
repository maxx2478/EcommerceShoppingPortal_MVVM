package com.manohar.ecommerceshoppingportal.util

import android.content.Context
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.manohar.ecommerceshoppingportal.R
import kotlinx.android.synthetic.main.content_main.*


fun AppCompatActivity.replaceFragmenty(fragment: Fragment?,
                                       allowStateLoss: Boolean = false,
                                       @IdRes containerViewId: Int) {

    val fl = container as FrameLayout
    fl.removeAllViews()
    supportFragmentManager.popBackStack();
    val ft = supportFragmentManager
        .beginTransaction()
        .replace(containerViewId, fragment!!)

    if (!supportFragmentManager.isStateSaved) {
        ft.commit()
    } else if (allowStateLoss) {
        ft.commitAllowingStateLoss()
    }
}

fun getProgressDrawable(context: Context): CircularProgressDrawable
{

    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    return circularProgressDrawable
}

fun ImageView.loadimage(uri:String?, progressDrawable: CircularProgressDrawable)
{
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher)


    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)

}