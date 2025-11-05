package com.example.assignment5

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

data class MarbleReading(val x: Float, val y: Float, val z: Float)

class MarbleRepository(private val sensorManager: SensorManager) {
    fun getGravityFlow(): Flow<MarbleReading> = channelFlow {
        val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        val sensor = gravitySensor ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (sensor == null) {
            return@channelFlow
        }

        val alpha = 0.8f
        val gravity = FloatArray(3)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent) {
                val values = event.values

                if (sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    gravity[0] = alpha * gravity[0] + (1 - alpha) * values[0]
                    gravity[1] = alpha * gravity[1] + (1 - alpha) * values[1]
                    gravity[2] = alpha * gravity[2] + (1 - alpha) * values[2]
                    trySendBlocking(MarbleReading(gravity[0], gravity[1], gravity[2]))
                } else {
                    trySendBlocking(MarbleReading(values[0], values[1], values[2]))
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
        }

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME)
        awaitClose { sensorManager.unregisterListener(listener) }
    }
}
