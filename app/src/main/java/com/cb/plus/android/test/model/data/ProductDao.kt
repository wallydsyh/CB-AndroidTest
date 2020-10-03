package com.cb.plus.android.test.model.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<ProductData>>

    @Query("SELECT * FROM product_table WHERE uid IN (:productId)")
    fun loadAllProducts(productId: IntArray): LiveData<ProductData>

    @Insert
    fun insert(vararg productData: ProductData)

    @Delete
    fun delete(user: ProductData)

    @Query("DELETE FROM product_table")
     fun deleteAll()
}