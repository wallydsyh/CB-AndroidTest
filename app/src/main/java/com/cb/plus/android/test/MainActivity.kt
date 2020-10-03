package com.cb.plus.android.test

import android.content.Intent
import android.os.Bundle
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
import com.cb.plus.android.test.data.OnEditProductInterface
import com.cb.plus.android.test.data.ProductData
import com.cb.plus.android.test.data.viewModel.ProductDataBaseViewModel
import com.cb.plus.android.test.data.viewModel.ViewModelFactoryDataBase
import com.cb.plus.android.test.databinding.ActivityMainBinding
import com.cb.plus.android.test.viewModel.ProductViewModel
import com.cb.plus.android.test.viewModel.ViewModelFactory
import com.google.zxing.integration.android.IntentIntegrator


class MainActivity : AppCompatActivity(), View.OnClickListener,
    OnEditProductInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productDataBaseViewModel: ProductDataBaseViewModel
    private lateinit var adapter: ProductListAdapter
    private var barcode = String()

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
        adapter.editProductInterface = this
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
        productDataBaseViewModel = ViewModelProvider(
            this,
            ViewModelFactoryDataBase(this.application)
        ).get(
            ProductDataBaseViewModel::class.java
        )
    }

    private fun setupObserver(barcode: String) {
        productViewModel.getProduct(barcode)
    }
/*
    private fun checkIfProductExist(productData: ProductData) {
        productDataBaseViewModel.isProductExist(productData)
        if (productDataBaseViewModel.isProductExist.hasObservers()) {
            productDataBaseViewModel.isProductExist.removeObserver { }
        } else {
            productDataBaseViewModel.isProductExist.observe(this, Observer {
                when (it) {
                    true -> {
                        getProductInDatabase(productData)
                        Toast.makeText(this, "already exist", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        productDataBaseViewModel.insert(productData)
                    }
                }
            })
        }
    }

    private fun getProductInDatabase(productData: ProductData) {

    }

 */

    private fun setupObserverProductDataBase() {

        productDataBaseViewModel.allProducts.observe(this, Observer {
            adapter.setProducts(it)
        })
        productViewModel.product.observe(this, Observer {
            val productData = ProductData(barcode, it.getProduct())
            productDataBaseViewModel.insert(productData)
        })
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
                barcode = result.contents
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListener()
    }

    override fun onEditProduct(productData: ProductData) {
        productDataBaseViewModel.update(productData)
    }
}