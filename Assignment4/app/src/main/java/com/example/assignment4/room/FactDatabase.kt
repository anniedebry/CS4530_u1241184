package com.example.assignment4.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactEntity::class], version = 1, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {
    abstract fun factDao(): FactDAO
}