package com.example.assignment2.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class CourseEntity(
    val department: String,
    val number: String,
    val location: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0) {
    fun getCourseInfo() = "$department $number | $location"
}