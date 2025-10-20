package com.app.lira

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.app.lira.ui.screens.LoginScreen
import com.app.lira.ui.theme.LiraTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController



private fun MainActivity.rememberSystemUiController() {
    TODO("Not yet implemented")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()

        setContent {
            LiraTheme {
                LoginScreen()
            }
        }
    }
}
