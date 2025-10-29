@file:OptIn(ExperimentalSharedTransitionApi::class)

package io.github.elyesmansour.floatingTabBarSample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import io.github.elyesmansour.floatingTabBar.FloatingTabBar
import io.github.elyesmansour.floatingTabBar.FloatingTabBarDefaults
import io.github.elyesmansour.floatingTabBar.rememberFloatingTabBarScrollConnection
import io.github.elyesmansour.floatingTabBarSample.screens.HomeScreen
import io.github.elyesmansour.floatingTabBarSample.screens.PodcastsScreen
import io.github.elyesmansour.floatingTabBarSample.screens.ProfileScreen
import io.github.elyesmansour.floatingTabBarSample.screens.SearchScreen

@Composable
fun PlantSky(
    topPadding: Modifier = Modifier,
    bottomPadding: Modifier = Modifier
) {
    val scrollConnection = rememberFloatingTabBarScrollConnection()
    var selectedTabKey by remember { mutableStateOf<Any>("home") }

    val hazeState = rememberHazeState()
    val hazeEffectModifier = remember {
        Modifier.hazeEffect(hazeState) { noiseFactor = 0f }
    }

    val leadingTabs = remember {
        listOf(
            PlantSkyTab("home", "Home", Icons.Default.Home),
            PlantSkyTab("podcasts", "Podcasts", Icons.Default.PlayArrow),
            PlantSkyTab("profile", "Profile", Icons.Default.Person)
        )
    }
    val trailingTab = remember {
        PlantSkyTab("search", "", Icons.Default.Search)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .then(topPadding)
    ) {
        // Main content area with animated tab switching
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Text(
                text = "PlantSky",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )

            AnimatedContent(
                targetState = selectedTabKey,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                modifier = Modifier
                    .weight(1f)
                    .hazeSource(hazeState)
            ) { target ->
                when (target) {
                    "home" -> HomeScreen(scrollConnection = scrollConnection)
                    "profile" -> ProfileScreen(scrollConnection = scrollConnection)
                    "podcasts" -> PodcastsScreen(scrollConnection = scrollConnection)
                    "search" -> SearchScreen(scrollConnection = scrollConnection)
                }
            }
        }

        Box(
            Modifier
                .then(
                    if (scrollConnection.isInline) {
                        Modifier.hazeEffect(hazeState) {
                            noiseFactor = 0f
                            progressive = HazeProgressive.verticalGradient(
                                startIntensity = 0.05f
                            )
                        }
                    } else {
                        Modifier
                    }
                )
                .align(Alignment.BottomCenter)
                .then(bottomPadding)
        ) {
            FloatingTabBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                selectedTabKey = selectedTabKey,
                scrollConnection = scrollConnection,
                tabBarContentModifier = hazeEffectModifier,
                inlineAccessory = { modifier, animatedVisibilityScope ->
                    PlantSkyAccessory(
                        modifier = modifier.then(hazeEffectModifier),
                        isInline = true,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                },
                expandedAccessory = { modifier, animatedVisibilityScope ->
                    PlantSkyAccessory(
                        modifier = modifier.then(hazeEffectModifier),
                        isInline = false,
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                },
                sizes = FloatingTabBarDefaults.sizes(
                    tabExpandedContentPadding = PaddingValues(
                        vertical = 6.dp,
                        horizontal = 16.dp
                    )
                )
            ) {
                val tabTint = @Composable { isSelected: Boolean ->
                    if (isSelected) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface
                }
                val indication = @Composable { ripple(color = MaterialTheme.colorScheme.tertiary) }
                
                leadingTabs.forEach { tabItem ->
                    tab(
                        key = tabItem.key,
                        title = {
                            Text(
                                text = tabItem.text,
                                color = tabTint(selectedTabKey == tabItem.key)
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tabItem.icon,
                                contentDescription = null,
                                tint = tabTint(selectedTabKey == tabItem.key)
                            )
                        },
                        onClick = { selectedTabKey = tabItem.key },
                        indication = indication
                    )
                }

                standaloneTab(
                    key = trailingTab.key,
                    icon = {
                        Icon(
                            imageVector = trailingTab.icon,
                            contentDescription = null,
                            tint = tabTint(selectedTabKey == trailingTab.key)
                        )
                    },
                    onClick = { selectedTabKey = trailingTab.key },
                    indication = indication
                )
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.PlantSkyAccessory(
    modifier: Modifier = Modifier,
    isInline: Boolean,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    val albumArtSize = if (isInline) 32.dp else 48.dp
    val albumArtCornerRadius = if (isInline) 6.dp else 8.dp
    val albumArtTextStyle = if (isInline) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.titleLarge
    val songTitleStyle = if (isInline) MaterialTheme.typography.bodySmall else MaterialTheme.typography.bodyMedium
    val contentPadding = if (isInline) PaddingValues(vertical = 8.dp, horizontal = 16.dp) else PaddingValues(vertical = 12.dp, horizontal = 24.dp)
    val songInfoPadding = if (isInline) 8.dp else 12.dp
    val playButtonSize = if (isInline) 32.dp else 40.dp
    val playIconSize = if (isInline) 20.dp else 24.dp

    Row(
        modifier = modifier.padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .sharedElement(
                    sharedContentState = rememberSharedContentState("album_art"),
                    animatedVisibilityScope = animatedVisibilityScope
                )
                .size(albumArtSize)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(albumArtCornerRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🎧",
                style = albumArtTextStyle,
                modifier = Modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState("artwork"),
                    enter = EnterTransition.None,
                    exit = ExitTransition.None,
                    animatedVisibilityScope = animatedVisibilityScope,
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                )
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = songInfoPadding)
        ) {
            Text(
                text = "The Secret Life of Succulents",
                style = songTitleStyle,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .sharedBounds(
                        sharedContentState = rememberSharedContentState("song_title"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .skipToLookaheadSize()
            )

            if (!isInline) {
                Text(
                    text = "Plant Whisperer Podcast",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )

                LinearProgressIndicator(
                    progress = { 0.35f },
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            }
        }

        // Playback Controls
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier.size(playButtonSize)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState("play_button"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(playIconSize)
                )
            }
        }
    }
}

private data class PlantSkyTab(
    val key: String,
    val text: String,
    val icon: ImageVector
)


