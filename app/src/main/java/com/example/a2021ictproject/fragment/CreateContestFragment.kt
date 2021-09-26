package com.example.a2021ictproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.a2021ictproject.R
import com.example.a2021ictproject.databinding.CreateContestFragmentBinding
import com.example.a2021ictproject.network.dto.request.ContestRequest
import com.example.a2021ictproject.viewmodel.CreateContestViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.sql.Timestamp

class CreateContestFragment : Fragment() {

    private val navController: NavController by lazy { findNavController() }

    private lateinit var binding: CreateContestFragmentBinding
    private val viewModel: CreateContestViewModel by viewModels()

    private var dueTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_contest_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.etDateCreateContest.setOnClickListener { showCalendar() }

        /* focus 일 때, 마지막에 원을 떼고, focus가 아닐 때 원을 붙임 */
        binding.etPrizeCreateContest.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setText((text.toString()).replace("원", ""))
                } else {
                    if (!text.isNullOrBlank())
                        // Todo (돈 단위별 콤마 작업하기)
                        setText("${text}원")
                }
            }
        }

        binding.btnCloseCreateContest.setOnClickListener { navigateToMain() }

        binding.btnConfirmCreateContest.setOnClickListener {
            viewModel.postCreateContest(getCreateContest())
        }
    }

    private fun observe() {
        viewModel.postCreateContestRes.observe(viewLifecycleOwner, {
            when (it?.code) {
                null -> {
                    Toast.makeText(requireContext(), "서버 통신에 실패했습니다.", Toast.LENGTH_SHORT)
                }

                in 200..299 -> {
                    navigateToMain()
                }

                else -> {
                    Toast.makeText(requireContext(), "대회 등록에 실패했습니다.", Toast.LENGTH_SHORT)
                }
            }
        })
    }

    private fun showCalendar() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("대회 기간 선택")
            .build()

        dateRangePicker.isCancelable = false
        dateRangePicker.addOnPositiveButtonClickListener {
            dueTime = it.second
            val text = "${viewModel.longTimeToDateAsString(it.first)} ~ ${viewModel.longTimeToDateAsString(it.second)}"
            binding.etDateCreateContest.setText(text)
        }

        dateRangePicker.show(requireActivity().supportFragmentManager, "Calendar")
    }

    private fun getCreateContest(): ContestRequest =
        ContestRequest(
            binding.etTitleCreateContest.text.toString(),
            binding.etContentCreateContest.text.toString(),
            dueTime,
            Integer.parseInt(binding.etPrizeCreateContest.text.toString().replace("원", ""))
        )


    private fun navigateToMain() {
        navController.navigate(R.id.action_createContestFragment_to_mainFragment)
    }
}
