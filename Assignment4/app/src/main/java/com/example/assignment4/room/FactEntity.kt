package com.example.assignment4.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactEntity(
    val fact: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0) {
    fun getCourseInfo() = fact
}