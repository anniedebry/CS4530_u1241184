package com.example.assignment4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.assignment4.ui.theme.Assignment4Theme
import kotlin.getValue
import com.example.assignment4.room.FactEntity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    val facts by vm.factList.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { vm.fetchFact() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Get Fun Fact!")
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(facts) { fact ->
                Text(
                    text = "• " + fact.fact,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}