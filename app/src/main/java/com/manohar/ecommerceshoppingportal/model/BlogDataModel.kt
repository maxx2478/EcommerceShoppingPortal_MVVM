package com.manohar.ecommerceshoppingportal.model

import com.google.gson.annotations.SerializedName

data class Blog(
        @SerializedName("title")
        val title: String?,
        @SerializedName("image")
        val image: String?

)