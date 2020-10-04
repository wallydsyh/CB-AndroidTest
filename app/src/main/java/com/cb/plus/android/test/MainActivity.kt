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
import com.cb.plus.android.test.utils.DisplayDialog
import com.cb.plus.android.test.viewModel.ProductViewModel
import com.cb.plus.android.test.viewModel.ViewModelFactory
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity(), View.OnClickListener,
    OnEditProductInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productDataBaseViewModel: ProductDataBaseViewModel
    private lateinit var adapter: ProductListAdapter
    private var barcode = String()
    private lateinit var scope: CoroutineScope


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListener()
        setupViewModel()
        scope = CoroutineScope(Job() + Dispatchers.Main)
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            setupObserverProductDataBase()
        }

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


    private suspend fun checkIfProductExist(productData: ProductData): Boolean {
        return productDataBaseViewModel.isProductExist(productData)
    }

    private suspend fun checkForProduct(productData: ProductData) {
        if (checkIfProductExist(productData)) {
            if (getProductInDatabase(productData) != null) {
                getProductInDatabase(productData)?.let { it1 ->
                    DisplayDialog.displayDialog(
                        this@MainActivity,
                        it1,
                        adapter.editProductInterface
                    )
                }
            }
            Toast.makeText(this@MainActivity, "Product exist", Toast.LENGTH_LONG).show()
        } else {
            productDataBaseViewModel.insert(productData)
            Toast.makeText(this@MainActivity, "Product don't exist", Toast.LENGTH_LONG)
                .show()

        }
    }

    private suspend fun getProductInDatabase(productData: ProductData): ProductData? {
        return productDataBaseViewModel.getProduct(productData)
    }


    private suspend fun setupObserverProductDataBase() {
        productDataBaseViewModel.allProducts.observe(this, Observer {
            adapter.setProducts(it)
        })
        productViewModel.product.observe(this, Observer {
            val productData = ProductData(barcode, it.getProduct())
            if (productData.product != null) {
                scope.launch {
                    checkForProduct(productData)
                }
            } else {
                Toast.makeText(this, "Product not found in database", Toast.LENGTH_LONG).show()
            }
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
                barcode = result.contents
                productViewModel.getProduct(barcode)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        removeListener()
        scope.cancel()
    }

    override fun onEditProduct(productData: ProductData) {
        productDataBaseViewModel.update(productData)
    }
}