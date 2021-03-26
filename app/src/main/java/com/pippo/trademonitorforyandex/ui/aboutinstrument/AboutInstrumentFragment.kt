package com.pippo.trademonitorforyandex.ui.aboutinstrument

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pippo.trademonitorforyandex.R
import com.pippo.trademonitorforyandex.databinding.FragmentAboutInstrumentBinding

class AboutInstrumentFragment : Fragment() {

    companion object {
        fun newInstance() = AboutInstrumentFragment()
    }

    private lateinit var viewModel: AboutInstrumentViewModel

    private lateinit var binding: FragmentAboutInstrumentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutInstrumentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AboutInstrumentViewModel::class.java)


    }

}