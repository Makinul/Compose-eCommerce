package com.makinul.bs23104.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makinul.bs23104.data.Data
import com.makinul.bs23104.data.Event
import com.makinul.bs23104.data.ProductResponse
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<Data<ProductResponse>>(Data.Loading)
    val products: StateFlow<Data<ProductResponse>> = _products.asStateFlow()

//    private val _products by lazy { MutableLiveData<Event<Data<ProductResponse>>>() }
//    val products: LiveData<Event<Data<ProductResponse>>> = _products

    private var productCache = listOf<Product>()

    fun fetchProducts(initialPage: Int) = viewModelScope.launch(Dispatchers.IO) {
        _products.value = Data.Loading
        val result = repository.fetchProducts(initialPage)
        if (result is Data.Success) {
            result.data.products.let { newProducts ->
                if (initialPage == 0) { // Assuming 0 is the first page
                    productCache = newProducts
                } else {
                    // Append if it's a subsequent page; ensure no duplicates if API might return them
                    val currentIds = productCache.map { it.id }.toSet()
                    productCache =
                        productCache + newProducts.filterNot { currentIds.contains(it.id) }
                }
            }
        }
        _products.value = result
    }

    fun getProductById(id: Int): Product? {
        return productCache.find { it.id == id }
    }

    private

    companion object {
        private const val TAG = "MainViewModel"
    }
}