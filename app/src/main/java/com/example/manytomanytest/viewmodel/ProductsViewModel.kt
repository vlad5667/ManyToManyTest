package com.example.manytomanytest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manytomanytest.model.ProductDescription
import com.example.manytomanytest.model.Products
import com.example.manytomanytest.network.State
import com.example.manytomanytest.network.products.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel : ViewModel() {

    private val repository = ProductsRepository()

    private val _products = MutableLiveData<State<Products>>()
    val products: LiveData<State<Products>>
        get() = _products

    fun fetchProducts() = viewModelScope.launch {
        _products.postValue(State.Loading)
        withContext(Dispatchers.IO) {
            _products.postValue(repository.getProducts())
        }
    }

    private val _productDescription = MutableLiveData<State<ProductDescription>>()
    val productDescription: LiveData<State<ProductDescription>>
        get() = _productDescription

    fun fetchProductDescription(id: String) = viewModelScope.launch {
        _productDescription.postValue(State.Loading)
        withContext(Dispatchers.IO) {
            _productDescription.postValue(repository.getProductDescription(id))
        }
    }
}