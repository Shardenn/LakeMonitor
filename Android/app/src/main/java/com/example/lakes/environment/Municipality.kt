package com.example.lakes.environment

data class Municipality(
    val Kunta_Id: Int,
    val Nimi: String, // name
    val NimiRuo: String,
    val Ely_Id: Int,
    val YmpVastuuEly_Id: Int, //ecology responsibility
    val Seutukunta_id: Int, // subregion
    val Maakunta_Id: Int, // region
    val YTunnus: String, //reg number
)