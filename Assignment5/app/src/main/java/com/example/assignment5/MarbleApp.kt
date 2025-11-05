package com.example.assignment5

import android.app.Application
import android.content.Context
import android.hardware.SensorManager

class MarbleApp : Application() {
    val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    val marbleRepository by lazy {
        MarbleRepository(sensorManager)
    }
}