package com.project.concon.fragment

import android.content.Intent
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
import com.project.concon.R
import com.project.concon.databinding.SignInFragmentBinding
import com.project.concon.utils.MessageUtils
import com.project.concon.utils.PreferenceUtils
import com.project.concon.viewmodel.SignInViewModel

class SignInFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var binding: SignInFragmentBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        binding.motionLayoutSignIn.transitionToStart()
        binding.motionLayoutSignIn.transitionToEnd()

        binding.fabCloseSignIn.setOnClickListener {
            navigateToIntro()
        }
    }

    private fun observe() = with(viewModel) {
        id.observe(viewLifecycleOwner) {
            idErr.value = when(it.isNullOrBlank()) {
                true -> getString(R.string.error_input_id)
                false -> ""
            }
        }

        pw.observe(viewLifecycleOwner) {
            pwErr.value = when(it.isNullOrBlank()) {
                true -> getString(R.string.error_input_pw)
                false -> ""
            }
        }

        postSignInRes.observe(viewLifecycleOwner) { it ->
            if (it != null) {
                when (it.msg) {
                    "fail" -> MessageUtils.showToast(requireContext(), "올바른 아이디, 비밀번호가 아님")

                    else -> {
                        PreferenceUtils.token = it.msg
                        navigateToMain()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "서버 통신 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToIntro() {
        navController.navigate(R.id.action_signInFragment_to_introFragment)
    }

    private fun navigateToMain() {
        startActivity(Intent(requireActivity(), com.project.concon.activity.MainActivity::class.java))
    }
}