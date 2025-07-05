package com.elyesmansour.floating_tab_bar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.elyesmansour.floating_tab_bar.ui.theme.FloatingTabBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FloatingTabBarTheme {
                FloatingTabBarPreview()
            }
        }
    }
}