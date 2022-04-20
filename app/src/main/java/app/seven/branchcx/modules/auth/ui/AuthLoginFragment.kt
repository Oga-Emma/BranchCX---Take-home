package app.seven.branchcx.modules.auth.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import app.seven.branchcx.MainActivity
import app.seven.branchcx.databinding.AuthLoginFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthLoginFragment : Fragment() {
    private var _binding: AuthLoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val authLoginViewModel: AuthLoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthLoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupUiEventListeners()
    }

    private fun setupUiEventListeners() {
        authLoginViewModel.uiEvents.observe(viewLifecycleOwner) { item ->
            when(item){
                is LoginUIEvent.LoginFailed -> {
                    binding.btnLogin.isEnabled = true
                    binding.loading.visibility = View.GONE

                    Snackbar.make(binding.root, item.message, Snackbar.LENGTH_LONG).show()
                }
                is LoginUIEvent.LoginSuccess -> {
                    context?.let {
                        MainActivity.launch(it)
                    }
                }
                LoginUIEvent.LoginInProgress -> {
                    binding.btnLogin.isEnabled = false
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupView() {
        binding.btnLogin.setOnClickListener {

            if(!authLoginViewModel.isValidEmail(binding.etEmail.text.toString())){
                binding.etlEmail.error = "Enter a valid username"
                return@setOnClickListener
            }else{
                binding.etlEmail.error = null
            }

            if(!authLoginViewModel.isValidPassword(binding.etPassword.text.toString())){
                binding.etlPassword.error = "Enter a valid password"
                return@setOnClickListener
            }else{
                binding.etlPassword.error = null
            }

            closeKeyboard()
            authLoginViewModel.performLogin(email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString())
        }

    }


    private fun closeKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}