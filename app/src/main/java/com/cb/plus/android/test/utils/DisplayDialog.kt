package com.cb.plus.android.test.utils

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.cb.plus.android.test.R
import com.cb.plus.android.test.data.OnEditProductInterface
import com.cb.plus.android.test.data.ProductData
import com.cb.plus.android.test.databinding.UpdateProductDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object DisplayDialog {
    fun displayDialog(
        context: Context,
        productData: ProductData,
        editProductInterface: OnEditProductInterface
    ) {
        val alert = MaterialAlertDialogBuilder(context)
        val view = LayoutInflater.from(context).inflate(R.layout.update_product_dialog, null)
        lateinit var binding: UpdateProductDialogBinding
        binding = DataBindingUtil.bind(view)!!
        binding.textViewProductName.setText(productData.product?.getProductName())
        binding.textViewProductExpirationDate.setText(productData.product?.getExpirationDate())
        alert.setView(view)
        alert.setCancelable(false)
        alert.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
            productData.product?.setProductName(binding.textViewProductName.text.toString())
            productData.product?.setProductExpiringDate(binding.textViewProductExpirationDate.text.toString())
            editProductInterface.onEditProduct(productData)
        })
        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, _ ->
            dialogInterface.dismiss()
        })
        alert.show()
    }

}