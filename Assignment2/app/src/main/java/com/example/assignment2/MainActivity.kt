package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.assignment2.ui.theme.Assignment2Theme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class Course (val department: String, val number: String, val location: String){
    fun getCourseInfo(): String {
        return "$department $number | $location"
    }
}

class MyViewModel : ViewModel()
{
    private val courseList = MutableStateFlow(listOf<Course>())
    var currentCourse by mutableStateOf<Course?>(null)

    //add new course to the list
    fun addCourse(department: String, number: String, location: String){
        courseList.value = courseList.value + Course(department, number, location)
    }

    //removes courses from the list
    fun removeCourse(course: Course) {
        courseList.value = courseList.value - course
        if(currentCourse == course) {
            currentCourse == null
        }
    }

    //setter for when user switches course selection
    fun setCurrentCourse(course: Course) {
        currentCourse = course
    }

    //clears the course selection
    fun clearCurrentCourse(course: Course) {
        currentCourse = null
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                //put code here
            }
        }
    }
}

