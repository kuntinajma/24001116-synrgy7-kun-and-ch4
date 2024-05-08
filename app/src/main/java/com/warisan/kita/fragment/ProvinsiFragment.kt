package com.warisan.kita.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.warisan.kita.Data
import com.warisan.kita.R
import com.warisan.kita.adapter.ProvinsiAdapter
import com.warisan.kita.databinding.FragmentProvinsiBinding
import com.warisan.kita.model.Provinsi
import com.warisan.kita.viewmodel.ProvinsiViewModel

class ProvinsiFragment : Fragment() {
    private lateinit var binding:FragmentProvinsiBinding
    private val model: ProvinsiViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentProvinsiBinding.inflate(inflater,container,false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        with(binding){
            rvProvinsi.layoutManager = LinearLayoutManager(requireContext())
            model.listProvinsi.observe(viewLifecycleOwner){
                rvProvinsi.adapter = ProvinsiAdapter(it,object : ProvinsiAdapter.ProvinsiAdapterListener{
                    override fun onClick(provinsi: Provinsi) {
                        val b = Bundle()
                        b.putSerializable("provinsi",provinsi)
                        findNavController().navigate(R.id.action_provinsiFragment_to_destinasiFragment,b)
                    }
                })
            }

        }

        model.setList(Data.LIST_PROVINSI);
        return binding.root
    }

}