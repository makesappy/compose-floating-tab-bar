# FloatingTabBar

An Android floating tab bar implementation written in Jetpack Compose that mimics the iOS 26 liquid glass tab bar behavior. Features smooth transitions between expanded and inline states with built-in scroll behavior handling.

https://github.com/user-attachments/assets/77f76481-f537-4728-974e-dc081d9be83c

## Features

- **Smooth State Transitions**: Seamlessly transitions between expanded and inline states based on scroll behavior
- **Customizable Scroll Behavior**: Control when the tab bar transitions (on scroll down, scroll up, or never)
- **Accessory Support**: Add custom accessories (like media players) that adapt to both states
- **Customize Accessory State Transitions**: Accessory states can be animated using the same shared element transition powering the floating tab bar animation

## Basic Usage

```kotlin
@Composable
fun BasicExample() {
    val scrollConnection = rememberFloatingTabBarScrollConnection()
    var selectedTabKey by remember { mutableStateOf("home") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Your main content with scroll support
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollConnection)
        ) {
            // Your scrollable content
        }

        FloatingTabBar(
            selectedTabKey = selectedTabKey,
            scrollConnection = scrollConnection,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            // Regular tabs
            tab(
                key = "home",
                title = { Text("Home") },
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                onClick = { selectedTabKey = "home" }
            )
            
            tab(
                key = "profile",
                title = { Text("Profile") },
                icon = { Icon(Icons.Default.Person, contentDescription = null) },
                onClick = { selectedTabKey = "profile" }
            )

            // Standalone tab
            standaloneTab(
                key = "search",
                title = { Text("Search") },
                icon = { Icon(Icons.Default.Search, contentDescription = null) },
                onClick = { selectedTabKey = "search" }
            )
        }
    }
}
```

## Usage with Accessory

```kotlin
@Composable
fun AccessoryExample() {
    val scrollConnection = rememberFloatingTabBarScrollConnection()
    var selectedTabKey by remember { mutableStateOf("home") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Your content...
        
        FloatingTabBar(
            selectedTabKey = selectedTabKey,
            scrollConnection = scrollConnection,
            inlineAccessory = { modifier, animatedVisibilityScope ->
                CompactMiniPlayer(
                    modifier = modifier,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            },
            expandedAccessory = { modifier, animatedVisibilityScope ->
                MiniPlayer(
                    modifier = modifier,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            // Your tabs...
        }
    }
}
```

## Apply background blurring using Haze by Chris Banes

```kotlin
@Composable
fun HazeBlurExample() {
    val scrollConnection = rememberFloatingTabBarScrollConnection()
    var selectedTabKey by remember { mutableStateOf("home") }
    val hazeState = rememberHazeState()

    Box {
        LazyColumn(Modifier.hazeSource(hazeState)) {
            // Your content
        }
        
        FloatingTabBar(
            selectedTabKey = selectedTabKey,
            scrollConnection = scrollConnection,
            tabBarContentModifier = Modifier.hazeEffect(hazeState),
            inlineAccessory = { modifier, animatedVisibilityScope ->
                CompactMiniPlayer(
                    modifier = modifier.hazeEffect(hazeState),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            },
            expandedAccessory = { modifier, animatedVisibilityScope ->
                MiniPlayer(
                    modifier = modifier.hazeEffect(hazeState),
                    animatedVisibilityScope = animatedVisibilityScope
                )
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
        ) {
            // Your tabs...
        }
    }
}
```

## License

```
Copyright 2025 Elyes Mansour
 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
