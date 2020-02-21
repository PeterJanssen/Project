package com.example.wensambulanceapp.data.entities

import java.util.*

data class Wish(
    var wishId: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    var image: String = "",
    val date: String = Date().time.toString()

) {
    override fun toString(): String {
        return "Titel:'$title'\nLocatie:'$location'\nDatum:'$date'"
    }
}



