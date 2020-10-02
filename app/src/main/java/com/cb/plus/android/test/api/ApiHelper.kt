package com.cb.plus.android.test.api


class ApiHelper(private val apiService: ApiService) {
    fun getProduct(barcode: String)= apiService.getProduct(barcode)
}