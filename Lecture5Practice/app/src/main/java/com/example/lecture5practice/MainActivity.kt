package com.example.lecture5practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.lecture5practice.ui.theme.Lecture5PracticeTheme
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lecture5PracticeTheme {
                LecturePractice()
            }
        }
    }
}


//used ComposeDEMO file shown during class, and uploaded to canvas, to aid in the rest of the implementation
@Composable
fun LecturePractice() {
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }
    var combinedText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        //first text field
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)) {
            OutlinedTextField(
                value = firstInput,
                onValueChange = { firstInput = it },
                label = { Text("First Input") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        //second text field
        Row(modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)) {
            OutlinedTextField(
                value = secondInput,
                onValueChange = { secondInput = it },
                label = { Text("Second Input") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        //button for combining both text fields
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { combinedText = firstInput + secondInput }) {
                Text("Combine")
            }
        }

        //combining the two text field inputs and displaying
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Result: $combinedText",
                color = Color.Black,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

}