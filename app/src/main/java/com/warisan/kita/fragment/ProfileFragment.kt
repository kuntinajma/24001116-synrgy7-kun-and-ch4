package com.warisan.kita.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.warisan.kita.R
import com.warisan.kita.databinding.FragmentLoginBinding
import com.warisan.kita.databinding.FragmentProfileBinding
import com.warisan.kita.viewmodel.LoginViewModel
import com.warisan.kita.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val model: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        with(binding){
            btnBack.setOnClickListener{
                findNavController().popBackStack()
            }
            btnChangePassword.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_ubahKataSandiFragment)
            }
            btnLogout.setOnClickListener{
                model.logout(requireContext())
            }
            btnDeleteAccount.setOnClickListener{
                model.deleteAccount(requireContext())
            }
            model.user.observe(viewLifecycleOwner){
                etEmail.setText(it.email)
                etName.setText(it.name)
                etNickname.setText(it.nickname)
                etPhone.setText(it.phone)
            }
            model.loading.observe(viewLifecycleOwner){
                btnDeleteAccount.visibility = if(it) View.GONE else View.VISIBLE
                pbLoading.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        model.deleteAccountSuccess.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        model.logoutSuccess.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        model.loadUser(requireContext());
        return binding.root
    }
}