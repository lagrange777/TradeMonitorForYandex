package com.pippo.trademonitorforyandex.ui.market

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pippo.trademonitorforyandex.R
import com.pippo.trademonitorforyandex.databinding.ItemInstrimentBinding
import com.pippo.trademonitorforyandex.datamodels.StockData
import java.util.*


class MarketInstrumentAdapter : RecyclerView.Adapter<MarketInstrumentAdapter.ViewHolder>() {


    private val diffCallback = object : DiffUtil.ItemCallback<StockData.StockSymbol>() {

        override fun areItemsTheSame(
            oldItem: StockData.StockSymbol,
            newItem: StockData.StockSymbol
        ): Boolean =
            oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(
            oldItem: StockData.StockSymbol,
            newItem: StockData.StockSymbol
        ): Boolean {
            return oldItem.tradeData?.p == newItem.tradeData?.c
        }
    }

    private val mDiffer = AsyncListDiffer(this, diffCallback)

    private var onFavClick: (String) -> Unit = { }

    fun getOnFavClickEvent(onClick: (String) -> Unit) {
        onFavClick = onClick
    }

    fun submitList(data: List<StockData.StockSymbol?>?) {
        mDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInstrimentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mDiffer.currentList[position], position % 2 == 0)
    }

    inner class ViewHolder(private val binding: ItemInstrimentBinding) : RecyclerView.ViewHolder(binding.root) {

        private val ticker
            get() = binding.itemInstrumentTicker

        private val company
            get() = binding.itemInstrumentCompany

        private val currentPrice
            get() = binding.itemInstrumentCurrentPrice

        private val change
            get() = binding.itemInstrumentChange

        private val fav
            get() = binding.itemInstrumentFav


        fun bind(stock : StockData.StockSymbol, isOdd: Boolean) {
            fav.setOnClickListener {
                onFavClick(stock.symbol)
            }
            fav.setImageResource(
                if (stock.isFav)
                    R.drawable.ic_is_fav
                else
                    R.drawable.ic_is_not_fav
            )

            if (isOdd) {
                binding.root.setBackgroundResource(R.drawable.bg_instrument_trans)
            } else {
                binding.root.setBackgroundResource(R.drawable.bg_instrument_gray)
            }
            ticker.text = stock.symbol
            company.text = stock.description
            stock.tradeData?.let {
                change.text = it.v.toString()
                currentPrice.text = if (it.p != 0.0) it.p.toString() else it.c.toString()
            }
        }


    }
}
