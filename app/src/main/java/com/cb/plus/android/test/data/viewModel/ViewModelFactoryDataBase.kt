package com.cb.plus.android.test.data.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactoryDataBase(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductDataBaseViewModel::class.java) -> {
                return ProductDataBaseViewModel(application) as T
            }
            else -> throw IllegalArgumentException("Unknown class name")
        }
    }

}