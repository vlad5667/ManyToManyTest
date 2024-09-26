package com.example.manytomanytest.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.manytomanytest.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopBar(
    title: String,
    color: Color = AppTheme.colors.pink,
    @DrawableRes navigationIcon: Int? = null,
    onNavigationClick: (() -> Unit)? = null,
    @DrawableRes actionButtonIcon: Int? = null,
    onActionButtonClick: (() -> Unit)? = null,
) = CenterAlignedTopAppBar(
    title = { Text(text = title, color = Color.White) },
    colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = color),
    navigationIcon = {
        if (navigationIcon != null) {
            Button(
                icon = navigationIcon,
                onClick = { onNavigationClick?.invoke() }
            )
        }
    },
    actions = {
        if (actionButtonIcon != null) {
            Button(
                icon = actionButtonIcon,
                onClick = { onActionButtonClick?.invoke() }
            )
        }
    }
)

@Composable
private fun Button(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) = IconButton(onClick = onClick) {
    Icon(
        painter = painterResource(id = icon),
        tint = Color.White,
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}