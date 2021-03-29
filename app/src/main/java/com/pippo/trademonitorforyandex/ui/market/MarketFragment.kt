package com.pippo.trademonitorforyandex.ui.market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.pippo.trademonitorforyandex.databinding.FragmentMarketBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MarketFragment : Fragment() {


    private lateinit var viewModel: MarketViewModel
    private lateinit var binding: FragmentMarketBinding
    private val adapter = MarketInstrumentAdapter()

    private val stockList
        get() = binding.fragmentMarketStockList
    private val searchField
        get() = binding.fragmentMarketSearch
    private val tabs
        get() = binding.tabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarketViewModel::class.java)
        setSearch()
        setStockList()
        setTabs()
    }

    private fun setSearch() {
        searchField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    processQuery(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    processQuery(it)
                }
                return false
            }

        })
    }

    private fun processQuery(query: String) {
        GlobalScope.launch(Dispatchers.IO) {
            viewModel.searchFilter = {
                query.toLowerCase(Locale.ROOT) in it.symbol.toLowerCase(Locale.ROOT) ||
                        query.toLowerCase(Locale.ROOT) in it.description.toLowerCase(Locale.ROOT)
            }
            adapter.submitList(viewModel.getFilteredStocks())
        }
    }

    private fun setTabs() {
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> viewModel.onlyFavs = false

                    1 -> viewModel.onlyFavs = true
                }
                adapter.submitList(viewModel.getFilteredStocks())
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

    private fun setStockList() {
        stockList.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        stockList.adapter = adapter
        adapter.getOnFavClickEvent {
            viewModel.addToFav(it, requireContext())
        }
        setViewModelListener()
    }

    private fun setViewModelListener() {
        viewModel.stockLD.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}