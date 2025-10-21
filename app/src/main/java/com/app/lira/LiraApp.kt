package com.app.lira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.app.lira.navigation.LiraNavGraph
import com.app.lira.ui.theme.LiraTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiraApp()
        }
    }
}

@Composable
fun LiraApp() {
    LiraTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            LiraNavGraph(navController = navController)
        }
    }
}
