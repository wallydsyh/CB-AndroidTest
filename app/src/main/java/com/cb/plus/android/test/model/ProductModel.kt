package com.cb.plus.android.test.model

import com.google.gson.annotations.SerializedName


class ProductModel {
    @SerializedName("product_name")
    private var productName: String? = null

    @SerializedName("image_small_url")
    private var productImageUrl: String? = null

    @SerializedName("expiration_date")
    private var productExpirationDate: String? = null


    fun getProductName(): String {
        return productName.toString()
    }

    fun getProductImage(): String {
        return productImageUrl.toString()
    }

    fun getExpirationDate(): String {
        return productExpirationDate.toString()
    }

    fun setProductName(name: String){
        productName = name
    }

    fun setProductExpiringDate(date: String) {
        productExpirationDate = date
    }
}