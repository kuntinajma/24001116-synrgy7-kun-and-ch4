package com.warisan.kita.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.warisan.kita.R
import com.warisan.kita.database.User
import com.warisan.kita.databinding.FragmentLoginBinding
import com.warisan.kita.databinding.FragmentUbahKataSandiBinding
import com.warisan.kita.viewmodel.ProfileViewModel
import com.warisan.kita.viewmodel.UbahKataSandiViewModel

class UbahKataSandiFragment : Fragment() {
    private lateinit var binding: FragmentUbahKataSandiBinding
    private val model: UbahKataSandiViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUbahKataSandiBinding.inflate(inflater,container,false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        with(binding){
            btnBack.setOnClickListener{
                requireActivity().onBackPressed()
            }
            btnSave.setOnClickListener{
                model.changePassword(etOldPassword.text.toString(),etPassword.text.toString(),requireContext())
            }
            model.errorPassword.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilPassword.isErrorEnabled=false
                }else{
                    tilPassword.error = it
                }
            }
            model.errorOldPassword.observe(viewLifecycleOwner){
                if(it.isEmpty()){
                    tilOldPassword.isErrorEnabled=false
                }else{
                    tilOldPassword.error = it
                }
            }
            model.changePasswordSuccess.observe(viewLifecycleOwner){
                if(it){
                    Toast.makeText(requireContext(),"Berhasil ubah password",Toast.LENGTH_LONG).show()
                    etOldPassword.setText(null)
                    etPassword.setText(null)
                }
            }
            model.loading.observe(viewLifecycleOwner){
                btnBack.visibility = if(it) View.GONE else View.VISIBLE
                pbLoading.visibility = if(it) View.VISIBLE else View.GONE
            }
        }
        return binding.root
    }
}