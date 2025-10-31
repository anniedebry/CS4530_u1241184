package com.example.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.assignment4.ui.theme.Assignment4Theme
import kotlin.getValue
import com.example.assignment4.room.FactEntity

class MainActivity : ComponentActivity() {

    private val vm: FactViewModel by viewModels { FactViewModel.FactViewModelProvider.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment4Theme {
                FactListView(vm)
            }
        }
    }
}

@Composable
fun FactListView(vm: FactViewModel) {

}