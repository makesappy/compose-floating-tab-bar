package io.github.elyesmansour.floatingTabBarSample

import androidx.compose.ui.window.ComposeUIViewController
import io.github.elyesmansour.floatingTabBarSample.ui.theme.FloatingTabBarTheme
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    FloatingTabBarTheme {
        PlantSky()
    }
}


