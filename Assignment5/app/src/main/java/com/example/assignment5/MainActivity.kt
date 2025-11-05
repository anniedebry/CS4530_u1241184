package com.example.assignment5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import com.example.assignment5.ui.theme.Assignment5Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

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
    val marble by viewModel.marbleState.collectAsStateWithLifecycle()
    val marbleSize = 40.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        contentAlignment = Alignment.Center
    ) {
        val density = LocalDensity.current
        val maxWidthPx = with(density) { maxWidth.toPx() }
        val maxHeightPx = with(density) { maxHeight.toPx() }
        val marblePx = with(density) { marbleSize.toPx() }

        LaunchedEffect(marble, maxWidthPx, maxHeightPx) {
            viewModel.clampPosition(maxWidthPx, maxHeightPx, marblePx)
        }

        val offsetXDp = with(density) { marble.x.toDp() }
        val offsetYDp = with(density) { marble.y.toDp() }

        Box(
            modifier = Modifier
                .offset(x = offsetXDp, y = offsetYDp)
                .size(marbleSize)
                .background(Color.Red, CircleShape)
        )
    }
}
