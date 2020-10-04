package com.cb.plus.android.test.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cb.plus.android.test.data.AppDatabase
import com.cb.plus.android.test.data.ProductData
import com.cb.plus.android.test.data.repository.ProductDataBaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDataBaseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductDataBaseRepository
    val allProducts: LiveData<List<ProductData>>

    init {
        val productDao = AppDatabase.getDatabase(application, viewModelScope).productDao()
        repository = ProductDataBaseRepository(productDao)
        allProducts = repository.allProducts

    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(productData: ProductData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(productData)
    }

    fun update(productData: ProductData) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(productData)
    }

    suspend fun isProductExist(productData: ProductData) =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.isProductExist(productData)
        }

    suspend fun getProduct(productData: ProductData) =
        withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
            repository.getProduct(productData)
        }
}