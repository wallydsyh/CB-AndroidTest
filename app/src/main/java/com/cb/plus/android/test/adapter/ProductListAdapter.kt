package com.cb.plus.android.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cb.plus.android.test.R
import com.cb.plus.android.test.databinding.RecyclerviewItemBinding
import com.cb.plus.android.test.data.OnEditProductInterface
import com.cb.plus.android.test.data.ProductData
import com.cb.plus.android.test.utils.DisplayDialog
import com.cb.plus.android.test.utils.ImageUtils

class ProductListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ProductListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var productData = emptyList<ProductData>()
    private lateinit var binding: RecyclerviewItemBinding

     lateinit var editProductInterface: OnEditProductInterface

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTexView = binding.textViewProductName
        val productImageView = binding.imageViewProduct
        val productExpiringDateTexView = binding.textViewProductExpirationDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        binding = DataBindingUtil.bind(itemView)!!
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val productData = productData[position]
        holder.productNameTexView.text = productData.product?.getProductName()
        holder.productExpiringDateTexView.text = productData.product?.getExpirationDate()
        ImageUtils().displayRoundImageFromUrl(
            holder.itemView.context,
            productData.product?.getProductImage(),
            holder.productImageView
        )
        holder.itemView.setOnClickListener {
            DisplayDialog.displayDialog(holder.itemView.context, productData, editProductInterface )

        }
    }

    internal fun setProducts(productData: List<ProductData>) {
        this.productData = productData
        notifyDataSetChanged()
    }

    override fun getItemCount() = productData.size



}