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
import androidx.recyclerview.widget.LinearLayoutManager
import com.cb.plus.android.test.adapter.ProductListAdapter
import com.cb.plus.android.test.api.ApiHelper
import com.cb.plus.android.test.api.ApiServiceImpl
import com.cb.plus.android.test.databinding.ActivityMainBinding
import com.cb.plus.android.test.model.ProductModel
import com.cb.plus.android.test.model.ProductResponse
import com.cb.plus.android.test.model.data.AppDatabase
import com.cb.plus.android.test.model.data.ProductData
import com.cb.plus.android.test.model.data.viewModel.ProductDataBaseViewModel
import com.cb.plus.android.test.viewModel.ProductViewModel
import com.cb.plus.android.test.viewModel.ViewModelFactory
import com.cb.plus.android.test.viewModel.ViewModelFactory1
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var TAG = MainActivity::class.java.simpleName
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productDataBaseViewModel: ProductDataBaseViewModel
    private lateinit var adapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListener()
        setupViewModel()
        setupObserverProductDataBase()
    }

    private fun setListener() {
        val recyclerView = binding.productRecycleView
        adapter = ProductListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
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
        productDataBaseViewModel = ViewModelProvider(this, ViewModelFactory1(this.application)).get(
            ProductDataBaseViewModel::class.java
        )
    }

    private fun setupObserver(barcode: String) {
        productViewModel.getProduct(barcode)
        if (productViewModel.product.hasObservers()) {
            productViewModel.product.removeObserver(Observer { })

        } else {
            productViewModel.product.observe(this, Observer {
                Log.d(TAG, "Product ${it.getProduct()?.getProductName()}")
            })
        }
    }

    private fun setupObserverProductDataBase() {
        if (productDataBaseViewModel.allProducts.hasObservers()) {
            productDataBaseViewModel.allProducts.removeObserver(Observer {})
        } else {
            productDataBaseViewModel.allProducts.observe(this, Observer {
                adapter.setProducts(it)
            })
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_scan_product -> {
                val integrator = IntentIntegrator(this)
                integrator.setOrientationLocked(false);
                integrator.initiateScan()
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