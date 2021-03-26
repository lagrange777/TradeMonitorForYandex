package com.pippo.trademonitorforyandex.ui.market

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pippo.trademonitorforyandex.R
import com.pippo.trademonitorforyandex.databinding.FragmentMarketBinding

class MarketFragment : Fragment() {

    companion object {
        fun newInstance() = MarketFragment()
    }

    private lateinit var viewModel: MarketViewModel
    private lateinit var binding: FragmentMarketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMarketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MarketViewModel::class.java)



    }

}