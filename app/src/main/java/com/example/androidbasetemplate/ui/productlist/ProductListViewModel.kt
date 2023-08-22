package com.example.androidbasetemplate.ui.productlist

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
class ProductListViewModel @Inject constructor(
    private val productUseCase: ProductUseCase,
) : ViewModel() {

    var productList: List<Product> = listOf()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getProducts()
        }
    }

    @VisibleForTesting
    private fun getProducts() {
        productList = getLocalProducts()
        // TODO: productList = productUseCase.getProducts()
    }

    private fun getLocalProducts(): List<Product> {
        return listOf(
            Product(
                "Product name 1",
                "This is an example of product description 1",
                imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            ),
            Product(
                "Product name 2",
                "This is an example of product description 2",
                imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            ),
            Product(
                "Product name 3",
                "This is an example of product description 3",
                imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            ),
            Product(
                "Product name 4",
                "This is an example of product description 4",
                imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            ),
            Product(
                "Product name 5",
                "This is an example of product description 5",
                imageUrl = "https://definicion.de/wp-content/uploads/2009/06/producto.png",
            ),
        )
    }
}
