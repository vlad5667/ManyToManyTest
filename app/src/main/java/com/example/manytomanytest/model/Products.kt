package com.example.manytomanytest.model

import com.example.manytomanytest.BuildConfig
import kotlinx.serialization.Serializable

@Serializable
data class Products(
    val title: String,
    val items: List<Product>
)

@Serializable
data class Product(
    val id: String,
    val name: String,
    val image: String?,
    val color: String
) {

    val fullImagePath: String
        get() = BuildConfig.FRONT_API_URL + image

    val hexColor: String
        get() = "#$color"
}

@Serializable
data class ProductDescription(
    val id: String,
    val text: String?
)
