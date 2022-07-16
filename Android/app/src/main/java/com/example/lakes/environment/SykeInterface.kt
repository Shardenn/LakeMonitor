package com.example.lakes.environment

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SykeInterface {
    val baseUrl = "http://rajapinnat.ymparisto.fi/"
    var municipalityService: MunicipalityService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MunicipalityService::class.java)

    var lakeService: LakeService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(LakeService::class.java)
}