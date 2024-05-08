package com.warisan.kita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.warisan.kita.databinding.ItemProvinsiBinding
import com.warisan.kita.model.Provinsi

class ProvinsiAdapter(
    private var listLoveLive: List<Provinsi>, private var listener: ProvinsiAdapterListener
) : RecyclerView.Adapter<ProvinsiAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemProvinsiBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindItem(provinsi: Provinsi) {
            with(bind) {
                tvProvinsi.text = provinsi.nama
            }
            Glide.with(itemView).load(provinsi.gambar).into(bind.ivProvinsi)
            itemView.setOnClickListener {
                listener.onClick(provinsi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapter =
            ItemProvinsiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(listLoveLive[position])
    }

    override fun getItemCount(): Int = listLoveLive.size

    public interface ProvinsiAdapterListener {
        fun onClick(provinsi: Provinsi)
    }
}