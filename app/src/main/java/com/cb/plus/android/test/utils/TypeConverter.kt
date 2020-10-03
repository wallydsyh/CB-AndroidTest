package com.cb.plus.android.test.utils

import androidx.room.TypeConverter
import com.cb.plus.android.test.model.ProductModel
import com.google.gson.Gson


class TypeConverter {
    @TypeConverter
    fun fromString(value: String?): ProductModel {
        return Gson().fromJson(value, ProductModel::class.java)
    }

    @TypeConverter
    fun fromProductModel(productModel: ProductModel): String? {
        return Gson().toJson(productModel)
    }
}