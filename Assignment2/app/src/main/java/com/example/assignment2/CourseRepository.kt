package com.example.assignment2

import com.example.assignment2.room.CourseDao
import com.example.assignment2.room.CourseEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CourseRepository(private val scope: CoroutineScope, private val dao: CourseDao) {

    val allCourses: Flow<List<CourseEntity>> = dao.getAllCourses()

    fun addCourse(department: String, number: String, location: String) {
        scope.launch {
            val course = CourseEntity(department, number, location)
            dao.insertCourse(course)
        }
    }

    fun deleteCourse(course: CourseEntity) {
        scope.launch {
            dao.deleteCourse(course)
        }
    }

    fun updateCourse(course: CourseEntity, newDepartment: String, newNumber: String, newLocation: String) {
        scope.launch {
            val updated = course.copy(department = newDepartment, number = newNumber, location = newLocation)
            dao.updateCourse(updated)
        }
    }
}