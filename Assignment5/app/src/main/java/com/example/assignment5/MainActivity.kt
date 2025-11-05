package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.assignment5.ui.theme.Assignment5Theme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment5Theme {
                val vm: MarbleViewModel = viewModel(factory = MarbleViewModel.Factory)
                MarbleScreen(vm)
            }
        }
    }
}

@Composable
fun MarbleScreen(viewModel: MarbleViewModel) {

}