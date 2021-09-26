package com.example.a2021ictproject.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2021ictproject.R
import com.example.a2021ictproject.viewmodel.ContestDetailViewModel

class ContestDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ContestDetailFragment()
    }

    private lateinit var viewModel: ContestDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contest_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContestDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}