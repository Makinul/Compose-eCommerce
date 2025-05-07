package com.makinul.bs23104.ui.home

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _products by lazy { MutableLiveData<Event<Data<ProductResponse>>>() }
    val products: LiveData<Event<Data<ProductResponse>>> = _products

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        _products.postValue(Event(Data.Loading))
        _products.postValue(Event(repository.fetchProducts()))
    }
}