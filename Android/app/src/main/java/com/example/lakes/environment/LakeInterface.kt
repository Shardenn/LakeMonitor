package com.example.lakes.environment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class LakeResponse(val value: List<Lake>)

interface LakeService{
    @GET("/api/jarvirajapinta/1.0/odata/Jarvi")
    fun getLakes(
        @Query("\$top", encoded = true) resultsCount: Int,
        @Query("\$filter", encoded = true) filter: String) : Call<LakeResponse>

}