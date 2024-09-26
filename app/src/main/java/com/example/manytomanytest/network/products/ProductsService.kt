package com.example.manytomanytest.network.products

import com.example.manytomanytest.BuildConfig
import com.example.manytomanytest.model.ProductDescription
import com.example.manytomanytest.model.Products
import com.example.manytomanytest.network.HttpClientFactory
import com.example.manytomanytest.network.State
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ProductsService(
    private val client: () -> HttpClient = { HttpClientFactory.create() }
) {

    suspend fun getProducts(): Products = client().use {
        it.get("${BuildConfig.FRONT_API_URL}/items/random").body()
    }

    suspend fun getProductDescription(id: String): ProductDescription = client().use {
        it.get("${BuildConfig.FRONT_API_URL}/texts/$id").body()
    }
}