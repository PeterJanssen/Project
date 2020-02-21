package com.example.wensambulanceapp.ui.wishes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wensambulanceapp.data.repositories.VolunteerRepository

@Suppress("UNCHECKED_CAST")
class WishesViewModelFactory(
    private val repository: VolunteerRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WishesViewModel(repository) as T
    }
}