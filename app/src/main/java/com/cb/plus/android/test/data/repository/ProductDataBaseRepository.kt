package com.cb.plus.android.test.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.cb.plus.android.test.data.ProductDao
import com.cb.plus.android.test.data.ProductData

class ProductDataBaseRepository(private val productDao: ProductDao) {
    val allProducts: LiveData<List<ProductData>> = productDao.getAllProducts()
    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun insert(productData: ProductData) {
        productDao.insert(productData)
    }

    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun update(productData: ProductData) {
        productDao.update(productData)
    }
    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun isProductExist(productData: ProductData): Boolean {
       return productDao.isProductExist(productData.id)
    }

    @WorkerThread
    @Suppress("RedundantSuspendModifier")
    suspend fun getProduct(productData: ProductData): ProductData {
       return productDao.getProduct(productData.id)
    }
}