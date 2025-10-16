package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment2.room.CourseEntity
import com.example.assignment2.ui.theme.Assignment2Theme

class MainActivity : ComponentActivity() {
    private val vm: CourseViewModel by viewModels { CourseViewModel.CourseViewModelProvider.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
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
            onSelect = { vm.selectCourse(it) },
            onDelete = { vm.removeCourse(it) },
            onEdit = { old, d, n, l -> vm.editCourse(old, d, n, l) }
        )
    } else {
        DetailViewPage(course = selected, vm = vm)
    }
}

@Composable
fun CourseListPage(
    courses: List<CourseEntity>,
    onAdd: (String, String, String) -> Unit,
    onSelect: (CourseEntity) -> Unit,
    onDelete: (CourseEntity) -> Unit,
    onEdit: (CourseEntity, String, String, String) -> Unit
) {
    var department by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var editingCourse by remember { mutableStateOf<CourseEntity?>(null) }
    var editDepartment by remember { mutableStateOf("") }
    var editNumber by remember { mutableStateOf("") }
    var editLocation by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(32.dp)) {
        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Department") }
        )
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Course Number") }
        )
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") }
        )

        Button(onClick = {
            onAdd(department, number, location)
            department = ""; number = ""; location = ""
        }) {
            Text("Add Course")
        }

        Spacer(Modifier.height(32.dp))

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
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable(enabled = false) {}
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
                            ) {
                                Text("Save")
                            }
                        }
                    }

                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { onSelect(course) },
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("${course.department} ${course.number} | ${course.location}", modifier = Modifier.weight(3f))
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
fun DetailViewPage(course: CourseEntity, vm: CourseViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp)
    ) {
        Text("Department: ${course.department}")
        Text("Course Number: ${course.number}")
        Text("Location: ${course.location}")

        Spacer(Modifier.height(10.dp))

        Button(onClick = { vm.clearCurrentCourse() }) {
            Text("Back")
        }
    }
}
