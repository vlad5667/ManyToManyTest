package com.example.manytomanytest.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil.compose.rememberAsyncImagePainter
import com.example.manytomanytest.R
import com.example.manytomanytest.components.CommonLoader
import com.example.manytomanytest.components.CommonTopBar
import com.example.manytomanytest.theme.AppTheme
import kotlinx.coroutines.delay

@Composable
fun ProductDetailsView(
    isLoading: Boolean,
    title: String,
    cardColor: Color,
    imageUrl: String?,
    description: String,
    onBackButtonClick: () -> Unit
) = Scaffold(
    topBar = {
        CommonTopBar(
            title = title,
            navigationIcon = R.drawable.ic_back,
            onNavigationClick = onBackButtonClick
        )
    },
    modifier = Modifier
        .fillMaxSize()
) { innerPadding ->
    ContentView(
        isLoading = isLoading,
        cardColor = cardColor,
        imageUrl = imageUrl,
        description = description,
        modifier = Modifier
            .padding(innerPadding)
    )
}

@Composable
private fun ContentView(
    isLoading: Boolean,
    cardColor: Color,
    imageUrl: String?,
    description: String,
    modifier: Modifier = Modifier
) {
    var shouldShowLoader by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isLoading) {
        if (!isLoading) {
            delay(1000L)
            shouldShowLoader = false
        } else {
            shouldShowLoader = true
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (shouldShowLoader) CommonLoader(modifier = Modifier.align(Alignment.TopCenter))

        AnimatedVisibility(
            visible = !shouldShowLoader,
            enter = fadeIn(tween(500))
        ) {
            Card(
                cardColor = cardColor,
                imageUrl = imageUrl,
                description = description
            )
        }
    }
}

@Composable
private fun Card(
    cardColor: Color,
    imageUrl: String?,
    description: String,
    modifier: Modifier = Modifier
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp, vertical = 24.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(cardColor)
        .padding(16.dp)
) {
    var isImageLoaded by remember { mutableStateOf(true) }
    if (imageUrl != null && isImageLoaded) {
        Image(
            painter = rememberAsyncImagePainter(
                model = imageUrl,
                onError = { isImageLoaded = false }
            ),
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )
    }

    Text(
        text = description,
        color = Color.White,
        style = AppTheme.typography.bodyL
    )
}

@Preview
@Composable
private fun ProductDetailsViewPreview() = ProductDetailsView(
    isLoading = false,
    title = "Tomato",
    cardColor = Color("#1D3461".toColorInt()),
    imageUrl = "https://test-task-server.mediolanum.f17y.com/images/cherry.png",
    description = "Lorem ipsum dolor sit amet...",
    onBackButtonClick = {}
)