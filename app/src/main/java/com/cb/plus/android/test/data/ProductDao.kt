package com.cb.plus.android.test.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {
    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<ProductData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg productData: ProductData)

    @Update
    fun update(vararg productData: ProductData)

    @Query("SELECT EXISTS(SELECT * FROM product_table WHERE id = :id)")
    fun isProductExist(id : String) : Boolean


    @Query("DELETE FROM product_table")
    fun deleteAll()
}