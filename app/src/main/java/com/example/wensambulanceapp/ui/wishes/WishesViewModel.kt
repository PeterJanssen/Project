package com.example.wensambulanceapp.ui.wishes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wensambulanceapp.data.entities.Wish
import com.example.wensambulanceapp.data.repositories.VolunteerRepository
import com.example.wensambulanceapp.util.Coroutines
import kotlinx.coroutines.Job

class WishesViewModel(val volunteerRepository: VolunteerRepository) : ViewModel() {
    private lateinit var job: Job

    private val _wishes = MutableLiveData<List<Wish>>()
    val wishes: LiveData<List<Wish>>
        get() = _wishes

    init {
        getReports()
    }

    private fun getReports() {
        job = Coroutines.ioThenMain(
            { volunteerRepository.getWishes() },
            { _wishes.value = it }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (::job.isInitialized) job.cancel()
    }
}