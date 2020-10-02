package com.cb.plus.android.test.repository

import com.cb.plus.android.test.api.ApiHelper

class ProductRepository(private val apiHelper: ApiHelper) {
    fun getProduct(barCode: String) = apiHelper.getProduct(barCode)

}