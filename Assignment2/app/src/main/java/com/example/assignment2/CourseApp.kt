package com.example.assignment2

import android.app.Application
import androidx.room.Room
import com.example.assignment2.room.CourseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CourseApp : Application() {
    private val scope = CoroutineScope(SupervisorJob())
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            CourseDatabase::class.java,
            "course_db"
        ).build()
    }
    val repository by lazy { CourseRepository(scope, database.courseDao()) }
}