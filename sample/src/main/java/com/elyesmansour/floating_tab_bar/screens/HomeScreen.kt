package com.elyesmansour.floating_tab_bar.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

data class PlantPost(
    val id: String,
    val username: String,
    val handle: String,
    val content: String,
    val timestamp: String,
    val likes: Int,
    val reposts: Int,
    val isLiked: Boolean = false,
    val isReposted: Boolean = false
)

@Composable
fun HomeScreen(
    scrollConnection: NestedScrollConnection,
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    val posts = remember {
        listOf(
            PlantPost(
                id = "1",
                username = "Plant Mama",
                handle = "@plantmama",
                content = "Just repotted my snake plant and it's looking so happy! 🐍🌱 The new terracotta pot is perfect for drainage.",
                timestamp = "2h",
                likes = 24,
                reposts = 3
            ),
            PlantPost(
                id = "2",
                username = "Succulent Sam",
                handle = "@succulentsam",
                content = "Hot take: Overwatering kills more plants than underwatering. Your plants don't need daily drinks! 💧",
                timestamp = "4h",
                likes = 156,
                reposts = 32
            ),
            PlantPost(
                id = "3",
                username = "Fern Fanatic",
                handle = "@fernlover",
                content = "Found this gorgeous Boston fern at the local nursery for $8! Sometimes the best plants are hiding in plain sight 🌿",
                timestamp = "6h",
                likes = 45,
                reposts = 7
            ),
            PlantPost(
                id = "4",
                username = "Monstera Mike",
                handle = "@monsteramike",
                content = "My monstera deliciosa just unfurled its 50th leaf! 🍃 Each one gets more fenestrated than the last. Plant parenthood is so rewarding.",
                timestamp = "8h",
                likes = 89,
                reposts = 15
            ),
            PlantPost(
                id = "5",
                username = "Cactus Queen",
                handle = "@cactusqueen",
                content = "Reminder: Desert plants need desert conditions! Don't put your cactus in a humid bathroom 🌵",
                timestamp = "12h",
                likes = 203,
                reposts = 48
            ),
            PlantPost(
                id = "6",
                username = "Herb Hero",
                handle = "@herbhero",
                content = "Fresh basil from my windowsill garden made tonight's pasta absolutely perfect! Nothing beats homegrown herbs 🌿🍝",
                timestamp = "1d",
                likes = 67,
                reposts = 12
            ),
            PlantPost(
                id = "7",
                username = "Fiddle Leaf Fig",
                handle = "@fiddleleaffig",
                content = "Yes, I'm dramatic. Yes, I'll drop leaves if you move me. Yes, I'm worth it. 💁‍♀️🌱",
                timestamp = "1d",
                likes = 334,
                reposts = 89
            ),
            PlantPost(
                id = "8",
                username = "Pothos Paul",
                handle = "@pothosp",
                content = "Propagated 15 pothos cuttings this week! Who wants some? Plant swaps are the best way to grow your collection 🌿",
                timestamp = "2d",
                likes = 78,
                reposts = 23
            )
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollConnection)
            .hazeSource(hazeState)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(posts) { post ->
            PlantPostCard(post = post)
        }
    }
}

@Composable
private fun PlantPostCard(
    post: PlantPost,
    modifier: Modifier = Modifier
) {
    var isLiked by remember { mutableStateOf(post.isLiked) }
    var isReposted by remember { mutableStateOf(post.isReposted) }
    var likeCount by remember { mutableStateOf(post.likes) }
    var repostCount by remember { mutableStateOf(post.reposts) }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.username.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = post.username,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = " ${post.handle}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                        Text(
                            text = " • ${post.timestamp}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp)
            )
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            isLiked = !isLiked
                            likeCount += if (isLiked) 1 else -1
                        }
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (isLiked) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = likeCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            isReposted = !isReposted
                            repostCount += if (isReposted) 1 else -1
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Repost",
                            tint = if (isReposted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = repostCount.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                IconButton(
                    onClick = { /* Share functionality */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}