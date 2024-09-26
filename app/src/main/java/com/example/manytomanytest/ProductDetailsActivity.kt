package com.example.manytomanytest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.manytomanytest.network.State
import com.example.manytomanytest.theme.AppTheme
import com.example.manytomanytest.utils.extensions.toColorIntSafe
import com.example.manytomanytest.view.ProductDetailsView
import com.example.manytomanytest.viewmodel.ProductsViewModel

class ProductDetailsActivity : ComponentActivity() {

    private val productsViewModel: ProductsViewModel by viewModels()

    private val productId
        get() = intent.getStringExtra(KEY_PRODUCT_ID)

    private val title
        get() = intent.getStringExtra(KEY_TITLE).orEmpty()

    private val cardColor
        get() = intent.getStringExtra(KEY_CARD_COLOR)

    private val imageUrl
        get() = intent.getStringExtra(KEY_IMAGE_URL)

    private var productDescription by mutableStateOf("")
    private var isLoading by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ProductDetailsView(
                    isLoading = isLoading,
                    title = title,
                    cardColor = cardColor?.toColorIntSafe()?.let { Color(it) } ?: Color.Black,
                    imageUrl = imageUrl,
                    description = productDescription,
                    onBackButtonClick = ::finish
                )
            }
        }

        observeProductDescription()
        fetchProductDescription()
    }

    private fun observeProductDescription() {
        productsViewModel.productDescription.observe(this) { result ->
            when (result) {
                is State.Loading -> isLoading = true

                is State.Success -> {
                    val productDescription = result.data ?: return@observe
                    this.productDescription = productDescription.text.orEmpty()
                    isLoading = false
                }

                is State.Failure -> {
                    Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                    isLoading = false
                }

                else -> isLoading = false
            }
        }
    }

    private fun fetchProductDescription() {
        productsViewModel.fetchProductDescription(id = productId ?: return)
    }

    companion object {
        const val KEY_PRODUCT_ID = "productId"
        const val KEY_TITLE = "title"
        const val KEY_CARD_COLOR = "cardColor"
        const val KEY_IMAGE_URL = "imageUrl"
    }
}