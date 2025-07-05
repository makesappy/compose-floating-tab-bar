@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.elyesmansour.floating_tab_bar

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.elyesmansour.floating_tab_bar.ui.theme.FloatingTabBarTheme
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@PreviewLightDark
@Composable
fun FloatingTabBarPreview() {
    FloatingTabBarTheme {
        val scrollConnection = rememberFloatingTabBarScrollConnection()
        val hazeState = rememberHazeState()
        var selectedTabKey by remember { mutableStateOf<Any>("home") }

        val tabs = remember {
            listOf(
                PreviewTab("home", "Home", Icons.Default.Home),
                PreviewTab("profile", "Profile", Icons.Default.Person)
            )
        }

        val standaloneTab = remember {
            PreviewTab("search", "Search", Icons.Default.Search)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // Main content area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Floating Tab Bar Demo",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Scroll to see the tab bar transition between expanded and inline states",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                // Scrollable content to trigger the transition
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .nestedScroll(scrollConnection)
                        .hazeSource(hazeState)
                ) {
                    items(20) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = "Content Item ${index + 1}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }

            Box(
                Modifier
                    .then(
                        if (scrollConnection.isInline) {
                            Modifier.hazeEffect(hazeState) {
                                progressive = HazeProgressive.verticalGradient(
                                    startIntensity = 0.05f
                                )
                            }
                        } else {
                            Modifier
                        }
                    )
                    .align(Alignment.BottomCenter)
            ) {
                FloatingTabBar(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    selectedTabKey = selectedTabKey,
                    scrollConnection = scrollConnection,
                    tabBarContentModifier = Modifier.hazeEffect(hazeState),
                    inlineAccessory = { modifier, animatedVisibilityScope ->
                        PreviewAccessory(
                            modifier = modifier.hazeEffect(hazeState),
                            isInline = true,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    },
                    expandedAccessory = { modifier, animatedVisibilityScope ->
                        PreviewAccessory(
                            modifier = modifier.hazeEffect(hazeState),
                            isInline = false,
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                    }
                ) {
                    tabs.forEach { tabItem ->
                        tab(
                            key = tabItem.key,
                            title = {
                                Text(
                                    text = tabItem.text,
                                    color = if (selectedTabKey == tabItem.key) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = tabItem.icon,
                                    contentDescription = null,
                                    tint = if (selectedTabKey == tabItem.key) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                                )
                            },
                            onClick = { selectedTabKey = tabItem.key }
                        )
                    }

                    standaloneTab(
                        key = standaloneTab.key,
                        title = {
                            Text(
                                text = standaloneTab.text,
                                color = if (selectedTabKey == standaloneTab.key) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = standaloneTab.icon,
                                contentDescription = null,
                                tint = if (selectedTabKey == standaloneTab.key) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                            )
                        },
                        onClick = { selectedTabKey = standaloneTab.key }
                    )
                }
            }
        }
    }
}

@Composable
private fun SharedTransitionScope.PreviewAccessory(
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
    val previousButtonSize = if (isInline) 28.dp else 32.dp
    val playButtonSize = if (isInline) 32.dp else 40.dp
    val nextButtonSize = if (isInline) 28.dp else 32.dp
    val previousIconSize = if (isInline) 16.dp else 20.dp
    val playIconSize = if (isInline) 20.dp else 24.dp
    val nextIconSize = if (isInline) 16.dp else 20.dp

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
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(albumArtCornerRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🎵",
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
                text = "Bohemian Rhapsody",
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
                    text = "Queen",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )

                LinearProgressIndicator(
                    progress = 0.35f,
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
                modifier = Modifier.size(previousButtonSize)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Previous",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState("previous_button"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(previousIconSize)
                )
            }

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

            IconButton(
                onClick = {},
                modifier = Modifier.size(nextButtonSize)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Next",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .sharedElement(
                            sharedContentState = rememberSharedContentState("next_button"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .size(nextIconSize)
                )
            }
        }
    }
}

private data class PreviewTab(
    val key: String,
    val text: String,
    val icon: ImageVector
)