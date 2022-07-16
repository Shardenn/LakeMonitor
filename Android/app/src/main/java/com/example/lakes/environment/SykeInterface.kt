package com.example.lakes.environment

import android.content.res.Resources
import com.example.lakes.R
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SykeInterface {
    var municipalityService: MunicipalityService = Retrofit.Builder().
        baseUrl("http://rajapinnat.ymparisto.fi/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MunicipalityService::class.java)
}