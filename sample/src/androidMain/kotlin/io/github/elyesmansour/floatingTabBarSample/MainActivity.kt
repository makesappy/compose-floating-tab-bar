package io.github.elyesmansour.floatingTabBarSample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import io.github.elyesmansour.floatingTabBarSample.ui.theme.FloatingTabBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightNavigationBars = false

        setContent {
            FloatingTabBarTheme {
                PlantSky(
                    topPadding = Modifier.windowInsetsPadding(WindowInsets.statusBars),
                    bottomPadding = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                )
            }
        }
    }
}


