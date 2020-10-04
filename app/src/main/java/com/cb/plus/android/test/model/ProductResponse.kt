package com.cb.plus.android.test.model


import com.google.gson.annotations.SerializedName


class ProductResponse {
    @SerializedName("product")
    private val productModel: ProductModel? = null

    fun getProduct(): ProductModel? {
        return productModel
    }
}