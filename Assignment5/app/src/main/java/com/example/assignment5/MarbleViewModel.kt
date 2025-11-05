package com.example.assignment5

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

data class MarbleState(val x: Float = 0f, val y: Float = 0f)

class MarbleViewModel(private val repository: MarbleRepository) : ViewModel() {

    private var velocityX = 0f
    private var velocityY = 0f
    private val friction = 0.98f
    private val scale = -50f
    private val frameTime = 0.02f

    private val _marbleState = MutableStateFlow(MarbleState())
    val marbleState: StateFlow<MarbleState> = _marbleState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getGravityFlow().collect { reading ->
                velocityX += frameTime * scale * reading.x
                velocityY += frameTime * scale * reading.y

                velocityX *= friction
                velocityY *= friction

                val newX = _marbleState.value.x + velocityX
                val newY = _marbleState.value.y - velocityY

                _marbleState.value = MarbleState(newX, newY)
            }
        }
    }

    fun clampPosition(maxWidth: Float, maxHeight: Float, marbleSize: Float) {
        val x = _marbleState.value.x
        val y = _marbleState.value.y

        val clampedX = min(max(x, -maxWidth / 2 + marbleSize / 2), maxWidth / 2 - marbleSize / 2)
        val clampedY = min(max(y, -maxHeight / 2 + marbleSize / 2), maxHeight / 2 - marbleSize / 2)

        _marbleState.value = MarbleState(clampedX, clampedY)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MarbleApp)
                MarbleViewModel(app.marbleRepository)
            }
        }
    }
}
