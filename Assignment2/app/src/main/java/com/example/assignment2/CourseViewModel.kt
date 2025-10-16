package com.example.assignment2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.example.assignment2.room.CourseEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class CourseViewModel(private val courseRepository: CourseRepository) : ViewModel() {
    val courseList: StateFlow<List<CourseEntity>> = courseRepository.allCourses
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    var currentCourse: CourseEntity? = null

    // Add a new course
    fun addCourse(department: String, number: String, location: String) {
        courseRepository.addCourse(department, number, location)
    }

    // Remove a course
    fun removeCourse(course: CourseEntity) {
        courseRepository.deleteCourse(course)
        if (currentCourse == course) currentCourse = null
    }

    fun selectCourse(course: CourseEntity) {
        currentCourse = course
    }

    fun clearCurrentCourse() {
        currentCourse = null
    }

    fun editCourse(oldCourse: CourseEntity, newDepartment: String, newNumber: String, newLocation: String) {
        courseRepository.updateCourse(oldCourse, newDepartment, newNumber, newLocation)
        if (currentCourse == oldCourse) {
            currentCourse = oldCourse.copy(
                department = newDepartment,
                number = newNumber,
                location = newLocation
            )
        }
    }

    object CourseViewModelProvider {
        val Factory = viewModelFactory {
            initializer {
                CourseViewModel(
                    (this[AndroidViewModelFactory.APPLICATION_KEY] as CourseApp).repository
                )
            }
        }
    }
}
