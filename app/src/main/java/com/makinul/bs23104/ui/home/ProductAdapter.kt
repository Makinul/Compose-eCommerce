package com.makinul.bs23104.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.makinul.bs23104.R
import com.makinul.bs23104.data.model.Product
import com.makinul.bs23104.databinding.ItemProductBinding
import com.makinul.bs23104.databinding.ItemProductFooterBinding
import com.makinul.bs23104.databinding.ItemProductHeaderBinding
import com.makinul.bs23104.utils.AppConstants
import com.makinul.bs23104.utils.AppConstants.KEY_FOOTER
import com.makinul.bs23104.utils.AppConstants.KEY_HEADER
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(
    private val list: List<Product>,
    private val listener: OnClickListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val viewHolder = when (viewType) {
            KEY_HEADER -> {
                HeaderViewHolder(
                    ItemProductHeaderBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }

            KEY_FOOTER -> { // footer view where it usually hide
                FooterViewHolder(
                    ItemProductFooterBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }

            else -> {
                ProductViewHolder(
                    ItemProductBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when (holder) {
            is ProductViewHolder -> holder.bindData(
                holder.itemView.context,
                position,
                payloads
            )

            is HeaderViewHolder -> holder.bindData(holder.itemView.context, position)
            is FooterViewHolder -> holder.bindData(holder.itemView.context, position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ProductViewHolder -> holder.bindData(
                holder.itemView.context,
                position
            )

            is HeaderViewHolder -> holder.bindData(holder.itemView.context, position)
            is FooterViewHolder -> holder.bindData(holder.itemView.context, position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return item.key
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener?.clickOnItem(
                    position = layoutPosition,
                    item = list[layoutPosition]
                )
            }
        }

        fun bindData(context: Context, position: Int, payloads: MutableList<Any> = arrayListOf()) {
            val product = list[position]

            // Load the image using Glide
            Glide.with(context)
                .load(product.thumbnail)
                .placeholder(R.drawable.image_search)
                .error(R.drawable.image_not_found)
                .into(binding.productImageView)

            binding.productTitleTextView.text = product.title
            binding.productBrandTextView.text = product.brand

            // Format the price
            val currencyFormat =
                NumberFormat.getCurrencyInstance(Locale.US) // Or use a locale based on user settings
            val formattedPrice = currencyFormat.format(product.price)
            binding.productPriceTextView.text = formattedPrice

            binding.productDiscountTextView.text = "-${product.discountPercentage}%"
            binding.productRatingBar.rating = product.rating.toFloat()
            binding.productRatingTextView.text =
                "(${String.format("%.2f", product.rating)})" //show 2 decimal points

            val stockAvailability = if (product.stock > 5) {
                "In Stock"
            } else if (product.stock <= 0) {
                "Out of Stock"
            } else {
                "Only 3 left!"
            }
            binding.productAvailabilityTextView.text = stockAvailability
            binding.productAvailabilityTextView.setTextColor(
                if (product.stock > 5)
                    context.getColor(R.color.availability_in_stock)
                else
                    context.getColor(android.R.color.holo_red_light)
            )

            binding.productCategoryTextView.text = product.category
        }
    }

    inner class HeaderViewHolder(
        private val binding: ItemProductHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(context: Context, position: Int) {

        }
    }

    inner class FooterViewHolder(
        private val binding: ItemProductFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(context: Context, position: Int) {
//            itemView.invisible()
//
//            if (isInViewMode) {
//                binding.viewReportCondition.gone()
//            } else {
//                binding.viewReportCondition.visible()
//            }
        }
    }

    interface OnClickListener {
        fun clickOnItem(position: Int, item: Product)
    }

    private fun showLog(message: String = "Test message") {
        AppConstants.showLog(TAG, message)
    }

    companion object {
        private const val TAG = "AssessmentDetailsAdapter"
    }
}