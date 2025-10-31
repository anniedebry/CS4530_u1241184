package com.example.assignment4

import android.app.Application
import androidx.room.Room
import com.example.assignment4.network.FactAPI
import com.example.assignment4.network.createKtorClient
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
    private val client by lazy { createKtorClient() }
    private val api by lazy { FactAPI(client) }
    val repository by lazy { FactRepository(scope, database.factDao(), api) }
}