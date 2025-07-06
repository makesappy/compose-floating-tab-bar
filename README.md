# FloatingTabBar

An Android floating tab bar implementation written in Jetpack Compose that mimics the iOS 26 liquid glass tab bar behavior. Features smooth transitions between expanded and inline states with built-in scroll behavior handling.

<img src="assets/demo.gif" width="250">

*FloatingTabBar sample app demo using a frosty background effect*

## Features

- **Expanded and Inline States**: Transition between both states when scrolling
- **Customizable Scroll Behavior**: Control when the tab bar transitions (on scroll down, scroll up, or never)
- **Accessory**: Add a custom accessory (like a media player) that adapts to both states
- **Customize Accessory Transitions**: Accessory states can be animated using the same shared element transition powering the floating tab bar animation
- **Customizable colors, shapes, and sizes**

## Basic Usage

```kotlin
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
```

## Usage with Accessory
The inline and expanded accessory composable lambdas are extension function of `SharedTransitionLayoutScope` and provide an `animatedVisibilityScope` parameter, so it's possible to customize the accessory transition between the two states to be in sync with the tab bar's transition.

```kotlin
FloatingTabBar(
    ...
    inlineAccessory = { modifier, animatedVisibilityScope ->
        CompactMiniPlayer(
            modifier = modifier,
            animatedVisibilityScope = animatedVisibilityScope
        )
    },
    expandedAccessory = { modifier, animatedVisibilityScope ->
        ExpandedMiniPlayer(
            modifier = modifier,
            animatedVisibilityScope = animatedVisibilityScope
        )
    }
) {
    // Your tabs...
}
```

## Background frosty effects using Haze by Chris Banes

```kotlin
Box {
    // Apply hazeSource modifier on content
    LazyColumn(Modifier.hazeSource(hazeState)) {
        // Your content
    }
    
    FloatingTabBar(
        ...
        // Use the tabBarContentModifier parameter to apply extra background effects
        tabBarContentModifier = Modifier.hazeEffect(hazeState),
        inlineAccessory = { modifier, animatedVisibilityScope ->
            CompactMiniPlayer(
                // Apply the hazeEffect modifier directly on the accessory composable
                modifier = modifier.hazeEffect(hazeState),
                animatedVisibilityScope = animatedVisibilityScope
            )
        },
        expandedAccessory = { modifier, animatedVisibilityScope ->
            MiniPlayer(
                modifier = modifier.hazeEffect(hazeState),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    ) {
        // Your tabs...
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
