package com.cb.plus.android.test.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cb.plus.android.test.R
import com.cb.plus.android.test.databinding.RecyclerviewItemBinding
import com.cb.plus.android.test.model.data.ProductData
import com.cb.plus.android.test.utils.ImageUtils

class ProductListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<ProductListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var productData = emptyList<ProductData>()
    private lateinit var binding: RecyclerviewItemBinding

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
        val current = productData[position]
        holder.productNameTexView.text = current.product?.getProductName()
        holder.productExpiringDateTexView.text = current.product?.getExpirationDate()
        ImageUtils().displayRoundImageFromUrl(
            holder.itemView.context,
            current.product?.getProductImage(),
            holder.productImageView
        )
    }

    internal fun setProducts(productData: List<ProductData>) {
        this.productData = productData
        notifyDataSetChanged()
    }

    override fun getItemCount() = productData.size
}