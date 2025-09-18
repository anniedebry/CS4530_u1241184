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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember

class Course (val department: String, val number: String, val location: String){
    fun getCourseInfo(): String {
        return "$department $number | $location"
    }
}

class CourseViewModel : ViewModel()
{
    private val _courseList = MutableStateFlow(listOf<Course>())
    val courseList: StateFlow<List<Course>> = _courseList
    var currentCourse by mutableStateOf<Course?>(null)

    //add new course to the list
    fun addCourse(department: String, number: String, location: String){
        _courseList.value = courseList.value + Course(department, number, location)
    }

    //removes courses from the list
    fun removeCourse(course: Course) {
        _courseList.value = courseList.value - course
        if(currentCourse == course) {
            currentCourse = null
        }
    }

    //setter for when user switches course selection
    fun setCurrentCourse(course: Course) {
        currentCourse = course
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                val vm: CourseViewModel = viewModel()
            }
        }
    }
}

@Composable
fun CourseApplication(vm: CourseViewModel) {
    val courses by vm.courseList.collectAsState()
    val selected = vm.currentCourse

    if (selected == null) {
        CourseListPage(
            courses = courses,
            onAdd = { d, n, l -> vm.addCourse(d, n, l) },
            onSelect = { vm.setCurrentCourse(it) },
            onDelete = { vm.removeCourse(it) }
        )
    }
}

@Composable
fun CourseListPage(courses: List<Course>, onAdd: (String, String, String) -> Unit, onSelect: (Course) -> Unit, onDelete: (Course) -> Unit) {
    var department by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
}
