package com.cb.plus.android.test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cb.plus.android.test.model.ProductModel


@Entity(tableName = "product_table")
data class ProductData(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "product") val product: ProductModel?
)