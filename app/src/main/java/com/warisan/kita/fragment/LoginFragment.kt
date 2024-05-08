package com.warisan.kita.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.warisan.kita.R
import com.warisan.kita.databinding.FragmentLoginBinding
import com.warisan.kita.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding:FragmentLoginBinding
    private val model: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        model.isLogged.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.action_loginFragment_to_provinsiFragment)
            }
        }
        model.checkLogin(requireContext())
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        with(binding){

            tvRegister.setOnClickListener{
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
            btnLogin.setOnClickListener {
                model.login(etEmail.text.toString(),etPassword.text.toString(),requireContext())
            }
            model.errorLogin.observe(viewLifecycleOwner){
                Toast.makeText(requireContext(),it,Toast.LENGTH_LONG).show()
            }
            model.loginSuccess.observe(viewLifecycleOwner){
                if(it){
                    findNavController().navigate(R.id.action_loginFragment_to_provinsiFragment)
                }
            }
            model.loading.observe(viewLifecycleOwner){
                btnLogin.visibility = if(it) View.GONE else View.VISIBLE
                pbLoading.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
        return binding.root
    }
}