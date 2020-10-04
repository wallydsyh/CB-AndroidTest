package com.cb.plus.android.test.api

import com.cb.plus.android.test.model.ProductResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{barcode}.json")
    fun getProduct(@Path("barcode") barcode: String): Single<ProductResponse>?

}