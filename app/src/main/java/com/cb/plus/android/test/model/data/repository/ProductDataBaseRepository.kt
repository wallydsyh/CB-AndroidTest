package com.cb.plus.android.test.model.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.model.data.ProductDao
import com.cb.plus.android.test.model.data.ProductData

class ProductDataBaseRepository(private val productDao: ProductDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allProducts: LiveData<List<ProductData>> = productDao.getAllProducts()

    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun insert(productData: ProductData) {
        productDao.insert(productData)
    }
}