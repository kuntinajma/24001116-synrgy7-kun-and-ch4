package com.warisan.kita.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warisan.kita.adapter.DestinasiAdapter
import com.warisan.kita.databinding.FragmentDestinasiBinding
import com.warisan.kita.model.Destinasi
import com.warisan.kita.model.Provinsi
import com.warisan.kita.viewmodel.DestinasiViewModel


class DestinasiFragment : Fragment() {
    private lateinit var binding: FragmentDestinasiBinding
    private val model: DestinasiViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding=FragmentDestinasiBinding.inflate(inflater,container,false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        (activity as AppCompatActivity?)!!.invalidateOptionsMenu()
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.rvDestinasi.layoutManager = layoutManager

        model.provinsi.observe(viewLifecycleOwner){
            (activity as AppCompatActivity?)!!.supportActionBar!!.title = it.nama
            binding.rvDestinasi.adapter = DestinasiAdapter(it.destinasi,object : DestinasiAdapter.DestinasiAdapterListener{
                override fun onClick(destinasi: Destinasi) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(destinasi.url))
                    startActivity(intent)
                }
            })
        }

        model.setProvinsi(arguments?.getSerializable("provinsi") as Provinsi)
        return binding.root
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }
}