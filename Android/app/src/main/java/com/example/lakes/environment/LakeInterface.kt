package com.example.lakes.environment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class LakeResponse(val value: List<Lake>)
data class LakePointsResponse(val value: List<LakePoint>)
data class TemperatureResponse(val value: List<MeasuredTemperature>)

interface LakeService{
    @GET("/api/jarvirajapinta/1.0/odata/Jarvi")
    fun getLakes(
        @Query("\$top", encoded = true) resultsCount: Int,
        @Query("\$filter", encoded = true) filter: String) : Call<LakeResponse>

    @GET("/api/Hydrologiarajapinta/1.1/odata/Paikka")
    fun getLakePoints(
        @Query("\$top") resultsCount: Int,
        @Query("\$filter") filter: String) : Call<LakePointsResponse>

    // Get measured temperatures at given point
    @GET("/api/Hydrologiarajapinta/1.1/odata/LampoPintavesi")
    fun getMeasuredTemperature(
        @Query("\$top") resultsCount: Int,
        @Query("\$filter") filter: String): Call<TemperatureResponse>

}