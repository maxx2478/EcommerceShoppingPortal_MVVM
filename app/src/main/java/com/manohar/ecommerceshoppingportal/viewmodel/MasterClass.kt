package com.manohar.ecommerceshoppingportal.viewmodel

import android.app.Application
import android.content.Context

class MasterClass : Application()
{

    override fun onCreate()
    {
        super.onCreate()
        MasterClass.appContext = this
    }

    companion object {

        lateinit  var appContext: Context

    }

}