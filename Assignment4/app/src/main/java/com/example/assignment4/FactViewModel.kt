package com.example.assignment4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.example.assignment4.room.FactEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FactViewModel(private val factRepository: FactRepository) : ViewModel() {
    val factList: StateFlow<List<FactEntity>> = factRepository.allFacts
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addFact(fact: String) {
        factRepository.addCourse(fact)
    }

    object FactViewModelProvider {
        val Factory = viewModelFactory {
            initializer {
                FactViewModel(
                    (this[AndroidViewModelFactory.APPLICATION_KEY] as FactApp).repository
                )
            }
        }
    }
}
