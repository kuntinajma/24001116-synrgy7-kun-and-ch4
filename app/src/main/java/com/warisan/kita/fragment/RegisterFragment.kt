package com.warisan.kita.fragment

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.warisan.kita.R
import com.warisan.kita.database.AppDatabase
import com.warisan.kita.database.User
import com.warisan.kita.databinding.FragmentLoginBinding
import com.warisan.kita.databinding.FragmentRegisterBinding
import com.warisan.kita.utils.SharedPrefUtils
import com.warisan.kita.viewmodel.ProvinsiViewModel
import com.warisan.kita.viewmodel.RegisterViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var db: AppDatabase
    private val model: RegisterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRegisterBinding.inflate(inflater,container,false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        with(binding){
            btnRegister.setOnClickListener {
                model.register(
                    etName.text.toString(),
                    etNickname.text.toString(),
                    etEmail.text.toString(),
                    etPhone.text.toString(),
                    etPassword.text.toString(),
                    requireContext()
                )
            }
            model.errorName.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilName.isErrorEnabled = false
                }else{
                    tilName.error = it
                }
            }
            model.errorNickname.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilNickname.isErrorEnabled = false
                }else{
                    tilNickname.error = it
                }
            }
            model.errorEmail.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilEmail.isErrorEnabled = false
                }else{
                    tilEmail.error = it
                }
            }
            model.errorPhone.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilPhone.isErrorEnabled = false
                }else{
                    tilPhone.error = it
                }
            }
            model.errorPassword.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilPassword.isErrorEnabled = false
                }else{
                    tilPassword.error = it
                }
            }
            model.registerSuccess.observe(viewLifecycleOwner){
                if(it) {
                    findNavController().navigate(R.id.action_registerFragment_to_provinsiFragment)
                }
            }
            model.loading.observe(viewLifecycleOwner){
                btnRegister.visibility = if(it) View.GONE else View.VISIBLE
                pbLoading.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
        return binding.root
    }
}