package com.example.manytomanytest

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.manytomanytest.model.Product
import com.example.manytomanytest.network.State
import com.example.manytomanytest.theme.AppTheme
import com.example.manytomanytest.view.ProductsView
import com.example.manytomanytest.viewmodel.ProductsViewModel

class ProductsActivity : ComponentActivity() {

    private val productsViewModel: ProductsViewModel by viewModels()

    private var productTitle: String? by mutableStateOf("")
    private var products by mutableStateOf<List<Product>>(emptyList())
    private var isLoading by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                ProductsView(
                    title = productTitle ?: "",
                    products = products,
                    onProductClick = ::openProductDetails,
                    isLoading = isLoading,
                    onRefreshButtonClick = ::fetchProducts
                )
            }
        }

        observeProducts()
        fetchProducts()
    }

    private fun observeProducts() = productsViewModel.products.observe(this) { result ->
        when (result) {
            is State.Loading -> isLoading = true

            is State.Success -> {
                val products = result.data ?: return@observe
                this.productTitle = products.title
                this.products = products.items
                this.isLoading = false
            }

            is State.Failure -> {
                Toast.makeText(this, result.errorMessage, Toast.LENGTH_SHORT).show()
                isLoading = false
            }

            else -> isLoading = false
        }
    }

    private fun fetchProducts() {
        productTitle = ""
        products = emptyList()

        productsViewModel.fetchProducts()
    }

    private fun openProductDetails(id: String) {
        val product = products.find { it.id == id } ?: return
        val intent = Intent(this, ProductDetailsActivity::class.java).apply {
            putExtra(ProductDetailsActivity.KEY_PRODUCT_ID, product.id)
            putExtra(ProductDetailsActivity.KEY_TITLE, product.name)
            putExtra(ProductDetailsActivity.KEY_CARD_COLOR, product.hexColor)
            putExtra(ProductDetailsActivity.KEY_IMAGE_URL, product.fullImagePath)
        }
        startActivity(intent)
    }
}