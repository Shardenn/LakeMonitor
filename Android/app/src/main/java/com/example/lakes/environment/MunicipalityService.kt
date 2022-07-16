package com.example.lakes.environment

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class MunicipalityResponse(val value: List<Municipality>)

interface MunicipalityService {
    @GET("/api/hakemistorajapinta/1.0/odata/Kunta")
    fun getMunicipalities(
        @Query("\$top", encoded = true) resultsCount: Int,
        @Query("\$filter", encoded = true) filter: String) : Call<MunicipalityResponse>

}