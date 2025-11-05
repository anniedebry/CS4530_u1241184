package com.example.assignment5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class MarbleViewModel(private val repository: MarbleRepository) : ViewModel() {

    val marbleReading = repository.getGyroFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MarbleReading(0f, 0f, 0f)
        )

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarbleApp)
                MarbleViewModel(application.marbleRepository)
            }
        }
    }
}
