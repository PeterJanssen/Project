package com.example.wensambulanceapp.ui.rulesAgreements

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RulesAgreementsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is rulesAgreements Fragment"
    }
    val text: LiveData<String> = _text
}