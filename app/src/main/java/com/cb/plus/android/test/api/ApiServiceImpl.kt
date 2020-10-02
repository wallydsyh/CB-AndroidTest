package com.cb.plus.android.test.api

import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.model.ProductResponse
import io.reactivex.Single
import retrofit2.Call

class ApiServiceImpl(private val apiService: ApiService? = ApiClient().getClient()?.create(ApiService::class.java)) : ApiService {

    override fun getProduct(barcode: String): Single<ProductResponse>? {
        return apiService?.getProduct(barcode)
    }
}