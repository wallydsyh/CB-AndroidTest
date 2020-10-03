package com.cb.plus.android.test.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.model.ProductResponse
import com.cb.plus.android.test.repository.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    var product = MutableLiveData<ProductResponse>()

    fun getProduct(barcode: String) {
        productRepository.getProduct(barcode)?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                product.postValue(it)
            }, {
                Log.d("Error", it.localizedMessage)
            })

    }
}