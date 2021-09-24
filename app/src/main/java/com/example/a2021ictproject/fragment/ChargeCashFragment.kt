package com.example.a2021ictproject.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2021ictproject.R
import com.example.a2021ictproject.viewmodel.ChargeCashViewModel

class ChargeCashFragment : Fragment() {

    companion object {
        fun newInstance() = ChargeCashFragment()
    }

    private lateinit var viewModel: ChargeCashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.charge_cash_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChargeCashViewModel::class.java)
        // TODO: Use the ViewModel
    }

}