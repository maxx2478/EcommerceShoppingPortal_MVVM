package com.manohar.ecommerceshoppingportal.ui

import android.app.Application
import android.content.Context

class MasterClass : Application()
{

    override fun onCreate()
    {
        super.onCreate()
        appContext = this
    }

    companion object {

        lateinit  var appContext: Context

    }

}