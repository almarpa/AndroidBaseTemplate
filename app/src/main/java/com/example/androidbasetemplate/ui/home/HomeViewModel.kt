package com.example.androidbasetemplate.ui.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidbasetemplate.domain.ProductUseCase
import com.example.androidbasetemplate.entity.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
) : ViewModel() {

    private lateinit var productList: List<Product>

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getProducts()
        }
    }

    @VisibleForTesting
    private suspend fun getProducts() {
        productList = productUseCase.getProducts()
    }
}
