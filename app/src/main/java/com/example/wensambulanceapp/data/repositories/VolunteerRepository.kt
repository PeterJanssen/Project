package com.example.wensambulanceapp.data.repositories

import com.example.wensambulanceapp.data.entities.Wish

class VolunteerRepository(private val firebase: FirebaseRepository) {
    fun getWishes() = firebase.getWishes()
    fun getPersonalDetails(userId: String) = firebase.getPersonalDetails(userId)
    fun addVolunteerToWish(userId: String, wishId: String) = firebase.addVolunteerToWish(userId, wishId)
    fun getUserId() = firebase.currentUser()!!.uid
}