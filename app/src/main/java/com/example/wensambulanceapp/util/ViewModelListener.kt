package com.example.wensambulanceapp.util

interface ViewModelListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}