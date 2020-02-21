package com.example.wensambulanceapp.data.repositories

import com.example.wensambulanceapp.data.entities.Badge
import com.example.wensambulanceapp.data.entities.Location
import com.example.wensambulanceapp.data.entities.LoginUser
import com.example.wensambulanceapp.data.entities.RegisterUser
import com.example.wensambulanceapp.data.enums.Diploma
import com.example.wensambulanceapp.data.enums.Specialization
import com.example.wensambulanceapp.data.enums.TShirtSize

class UserRepository(private val firebase: FirebaseRepository) {
    fun login(email: String, password: String) =
        firebase.login(LoginUser(email = email, password = password))

    fun register(
        email: String,
        password: String,
        badge: Badge,
        diploma: Diploma,
        firstName: String,
        lastName: String,
        location: Location,
        phoneNumber: String,
        socialNumber: String,
        sizeTShirt: TShirtSize,
        specialization: Specialization
    ) =
        firebase.register(
            RegisterUser(
                email = email,
                badge = badge,
                diploma = diploma,
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                socialNumber = socialNumber,
                sizeTShirt = sizeTShirt,
                specialization = specialization,
                location = location
            ),
            password = password
        )

    fun currentUser() = firebase.currentUser()
}