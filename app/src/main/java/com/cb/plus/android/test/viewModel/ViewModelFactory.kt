package com.cb.plus.android.test.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cb.plus.android.test.api.ApiHelper
import com.cb.plus.android.test.data.viewModel.ProductDataBaseViewModel
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
            modelClass.isAssignableFrom(ProductDataBaseViewModel::class.java) -> {
                return ProductDataBaseViewModel(Application()) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}