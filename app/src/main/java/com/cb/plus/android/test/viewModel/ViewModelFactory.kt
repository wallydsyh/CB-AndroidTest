package com.cb.plus.android.test.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cb.plus.android.test.api.ApiHelper
import com.cb.plus.android.test.repository.ProductRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                return ProductViewModel(
                    ProductRepository(
                        apiHelper
                    )
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}