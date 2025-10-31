package com.example.assignment4

import android.app.Application
import androidx.room.Room
import com.example.assignment4.room.FactDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FactApp: Application() {
    private val scope = CoroutineScope(SupervisorJob())
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            FactDatabase::class.java,
            "fact_db"
        ).build()
    }
    val repository by lazy { FactRepository(scope, database.factDao()) }
}