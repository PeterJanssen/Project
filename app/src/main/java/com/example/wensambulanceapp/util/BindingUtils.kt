@file:JvmName("BindingUtils")

package com.example.wensambulanceapp.util

import androidx.databinding.InverseMethod
import com.example.wensambulanceapp.data.enums.Diploma
import com.example.wensambulanceapp.data.enums.Specialization
import com.example.wensambulanceapp.data.enums.TShirtSize

@InverseMethod("positionToDiploma")
fun diplomaToPosition(diploma: Diploma?): Int {
    return diploma?.ordinal ?: 0
}


fun positionToDiploma(position: Int): Diploma {
    return Diploma.values()[position]
}


@InverseMethod("positionToSpecialization")
fun specializationToPosition(specialization: Specialization?): Int {
    return specialization?.ordinal ?: 0
}


fun positionToSpecialization(position: Int): Specialization {
    return Specialization.values()[position]
}

@InverseMethod("positionToShirtSize")
fun shirtSizeToPosition(tShirtSize: TShirtSize?): Int {
    return tShirtSize?.ordinal ?: 0
}


fun positionToShirtSize(position: Int): TShirtSize {
    return TShirtSize.values()[position]
}