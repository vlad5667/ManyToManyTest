package com.example.manytomanytest.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import coil.compose.rememberAsyncImagePainter
import com.example.manytomanytest.R
import com.example.manytomanytest.components.CommonLoader
import com.example.manytomanytest.components.CommonTopBar
import com.example.manytomanytest.model.Product
import com.example.manytomanytest.theme.AppTheme
import com.example.manytomanytest.utils.extensions.clickable
import com.example.manytomanytest.utils.extensions.toColorIntSafe
import kotlinx.coroutines.delay

@Composable
fun ProductsView(
    title: String,
    products: List<Product>,
    onProductClick: (id: String) -> Unit,
    isLoading: Boolean,
    onRefreshButtonClick: () -> Unit
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

    Scaffold(
        topBar = {
            CommonTopBar(
                title = title.takeUnless { shouldShowLoader }.orEmpty(),
                actionButtonIcon = R.drawable.ic_refresh,
                onActionButtonClick = onRefreshButtonClick
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { innerPadding ->
        ContentView(
            products = products,
            onProductClick = onProductClick,
            shouldShowLoader = shouldShowLoader,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ContentView(
    products: List<Product>,
    onProductClick: (id: String) -> Unit,
    shouldShowLoader: Boolean,
    modifier: Modifier = Modifier
) = Box(
    modifier = modifier
        .fillMaxSize()
) {
    if (shouldShowLoader) CommonLoader(modifier = Modifier.align(Alignment.TopCenter))

    AnimatedVisibility(
        visible = !shouldShowLoader,
        enter = fadeIn(tween(500)) + slideInVertically(initialOffsetY = { -300 })
    ) {
        Products(products = products, onProductClick = onProductClick)
    }
}

@Composable
private fun Products(
    products: List<Product>,
    onProductClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
) = LazyColumn(
    modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 32.dp)
) {
    items(products, key = { it.id }) { product ->
        Product(product = product, onClick = { onProductClick(product.id) })
    }
}

@Composable
private fun Product(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) = Row(
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = modifier
        .padding(top = 24.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(product.hexColor.toColorIntSafe()?.let { Color(it) } ?: Color.Black)
        .clickable(indication = null, onClick = onClick)
        .padding(horizontal = 32.dp, vertical = 8.dp)
) {
    Text(
        text = product.name,
        color = Color.White,
        style = AppTheme.typography.bodyL
    )

    Image(
        painter = rememberAsyncImagePainter(product.fullImagePath),
        contentDescription = "${product.name} image",
        modifier = Modifier
            .size(56.dp)
    )
}

@Preview
@Composable
private fun ProductsViewPreview() = ProductsView(
    title = "Berries",
    products = listOf(
        Product(
            id = "cherry",
            name = "Cherry",
            image = "/images/cherry.png",
            color = "1D3461"
        )
    ),
    onProductClick = {},
    isLoading = false,
    onRefreshButtonClick = {}
)