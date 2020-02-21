package com.example.wensambulanceapp.util

import com.google.firebase.database.DataSnapshot

interface OnGetDataListener {
    fun onSuccess(dataSnapshot: DataSnapshot?)
    fun onStart()
    fun onFailure()
}