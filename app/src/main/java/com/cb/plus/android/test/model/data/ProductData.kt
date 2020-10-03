package com.cb.plus.android.test.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.model.ProductResponse


@Entity (tableName = "product_table")
data class ProductData(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "product") val product: ProductModel?
)