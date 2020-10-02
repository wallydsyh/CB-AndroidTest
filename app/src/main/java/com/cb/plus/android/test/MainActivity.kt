package com.cb.plus.android.test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cb.plus.android.test.api.ApiClient
import com.cb.plus.android.test.api.ApiHelper
import com.cb.plus.android.test.api.ApiService
import com.cb.plus.android.test.api.ApiServiceImpl
import com.cb.plus.android.test.databinding.ActivityMainBinding
import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.model.ProductResponse
import com.cb.plus.android.test.viewModel.ProductViewModel
import com.cb.plus.android.test.viewModel.ViewModelFactory
import com.google.zxing.integration.android.IntentIntegrator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var TAG = MainActivity::class.java.simpleName
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListener()
        setupViewModel()
    }

    private fun setListener() {
        binding.buttonScanProduct.setOnClickListener(this)
    }

    private fun removeListener() {
        binding.buttonScanProduct.setOnClickListener(null)

    }

    private fun setupViewModel() {
        productViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(
            ProductViewModel::class.java
        )
    }

    fun setupObserver(barcode: String){
        productViewModel.getProduct(barcode)
        productViewModel.product.observe(this, Observer {
            Log.d(TAG, "Product ${it.getProduct()?.getProductName()}")
        })

    }
/*
    private fun scanProduct(barcode: String) {
        val apiService: ApiService? = ApiClient().getClient()?.create(ApiService::class.java)
        val call: Call<ProductResponse>? = apiService?.getProduct(barcode)
        call!!.enqueue(object : Callback<ProductResponse?> {
            override fun onResponse(
                call: Call<ProductResponse?>?,
                response: Response<ProductResponse?>
            ) {
                val productModel: ProductModel? = response.body()?.getProduct()
                Log.d(TAG, "Product $productModel")
            }

            override fun onFailure(
                call: Call<ProductResponse?>?,
                t: Throwable
            ) {
                // Log error here since request failed
                Log.e(TAG, t.localizedMessage)
            }
        })

    }

 */

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_scan_product -> {
                IntentIntegrator(this).initiateScan()
            }
        }

    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        val result =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                setupObserver(result.contents)
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListener()
    }
}