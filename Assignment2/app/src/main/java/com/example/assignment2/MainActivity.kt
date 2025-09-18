package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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

    //clear selected course
    fun clearCurrentCourse() {
        currentCourse = null
    }

    //edit a currently existing course
    fun editCourse(oldCourse: Course, newDepartment: String, newNumber: String, newLocation: String) {
        _courseList.value = _courseList.value.map {
            if (it == oldCourse) Course(newDepartment, newNumber, newLocation) else it
        }
        if (currentCourse == oldCourse) {
            currentCourse = Course(newDepartment, newNumber, newLocation)
        }
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

    //if no course has been selected, keep displaying list of courses added
    if (selected == null) {
        CourseListPage(
            courses = courses,
            onAdd = { d, n, l -> vm.addCourse(d, n, l) },
            onSelect = { vm.currentCourse(it) },
            onDelete = { vm.removeCourse(it) },
            onEdit = { old, d, n, l -> vm.editCourse(old, d, n, l) }
        )
    } else {
        //go to detail view page if a course is selected
        DetailViewPage(course = selected, vm = vm)
    }
}

@Composable
fun CourseListPage(courses: List<Course>, onAdd: (String, String, String) -> Unit, onSelect: (Course) -> Unit, onDelete: (Course) -> Unit, onEdit: (Course, String, String, String) -> Unit) {
    var department by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    //course being edited
    var editingCourse by remember { mutableStateOf<Course?>(null) }
    var editDepartment by remember { mutableStateOf("") }
    var editNumber by remember { mutableStateOf("") }
    var editLocation by remember { mutableStateOf("") }

    //text fields for adding course data
    Column(Modifier.fillMaxSize().padding(32.dp)) {
        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Department") })
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Course Number") })
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") })

        //adds class to course list
        Button(onClick = {
            onAdd(department, number, location)
            department = ""; number = ""; location = ""
        }) {
            Text("Add Course")
        }

        Spacer(Modifier.height(32.dp))

        //displays all added courses
        //Lazycolumn with UI modifiers to have editing look better
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(courses) { course ->
                if (editingCourse == course) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp)
                            .clickable(enabled = false) {} //disables course selecting while editing a course
                    ) {
                        OutlinedTextField(
                            value = editDepartment,
                            onValueChange = { editDepartment = it },
                            label = { Text("Department") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = editNumber,
                            onValueChange = { editNumber = it },
                            label = { Text("Course Number") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = editLocation,
                            onValueChange = { editLocation = it },
                            label = { Text("Location") },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    onEdit(course, editDepartment, editNumber, editLocation)
                                    editingCourse = null
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Save") }
                        }
                    }

                } else {
                    //normal row view
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { onSelect(course) },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(course.getCourseInfo(), modifier = Modifier.weight(3f))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TextButton(onClick = {
                                editingCourse = course
                                editDepartment = course.department
                                editNumber = course.number
                                editLocation = course.location
                            }) { Text("Edit") }
                            TextButton(onClick = { onDelete(course) }) { Text("Delete") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailViewPage(course: Course, vm: CourseViewModel = viewModel()) {
    //displays all relevant information for the selected course
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp)
    ) {
        Text("Department: ${course.department}")
        Text("Course Number: ${course.number}")
        Text("Location: ${course.location}")

        Spacer(Modifier.height(10.dp))

        //sends user back to the list view page
        Button(onClick = { vm.clearCurrentCourse() }) {
            Text("Back")
        }
    }
}
