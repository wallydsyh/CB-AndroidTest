package com.cb.plus.android.test.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cb.plus.android.test.api.ApiHelper
import com.cb.plus.android.test.model.data.viewModel.ProductDataBaseViewModel
import com.cb.plus.android.test.repository.ProductRepository

class ViewModelFactory1(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductDataBaseViewModel::class.java) -> {
                return ProductDataBaseViewModel(application) as T


            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}