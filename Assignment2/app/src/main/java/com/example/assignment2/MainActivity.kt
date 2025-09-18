package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.unit.dp

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
    fun currentCourse(course: Course) {
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
                CourseApplication(vm)
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
            onSelect = { vm.currentCourse(it) },
            onDelete = { vm.removeCourse(it) }
        )
    }
}

@Composable
fun CourseListPage(courses: List<Course>, onAdd: (String, String, String) -> Unit, onSelect: (Course) -> Unit, onDelete: (Course) -> Unit) {
    var department by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(32.dp)) {
        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Department") })
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Number") })
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") })

        Button(onClick = {
            onAdd(department, number, location)
            department = ""; number = ""; location = ""
        }) {
            Text("Add Course")
        }

        Spacer(Modifier.height(32.dp))

        LazyColumn {
            items(courses) { course ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onSelect(course) },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(course.getCourseInfo())
                    TextButton(onClick = { onDelete(course) }) { Text("Delete") }
                }
            }
        }
    }
}

@Composable
fun DetailViewPage(course: Course, vm: CourseViewModel = viewModel()) {
    
}
