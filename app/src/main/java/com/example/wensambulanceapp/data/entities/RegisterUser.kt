package com.example.wensambulanceapp.data.entities

import com.example.wensambulanceapp.data.enums.Diploma
import com.example.wensambulanceapp.data.enums.Specialization
import com.example.wensambulanceapp.data.enums.TShirtSize

data class RegisterUser(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val socialNumber: String,
    val sizeTShirt: TShirtSize,
    val badge: Badge,
    val location: Location,
    val diploma: Diploma,
    val specialization: Specialization
)