package com.example.lakes.environment

data class Lake(
    val Jarvi_Id: Int, // Lake
    val Nro: String, // Number
    val Nimi: String, // Name
    val H_Kunta_Id: Int, // Municipality id
    val KuntaNimi: String, // Municipality name
    val KoordErLat: String, // Latitude
    val KoordErLong: String, // Longitude
)
