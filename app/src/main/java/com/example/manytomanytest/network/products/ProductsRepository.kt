package com.example.manytomanytest.network.products

import com.example.manytomanytest.network.BaseRepository

class ProductsRepository(
    private val productsService: ProductsService = ProductsService()
) : BaseRepository() {

    suspend fun getProducts() = request { productsService.getProducts() }

    suspend fun getProductDescription(id: String) = request {
        productsService.getProductDescription(id)
    }
}