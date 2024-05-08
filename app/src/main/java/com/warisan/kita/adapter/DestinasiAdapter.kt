package com.warisan.kita.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.warisan.kita.databinding.ItemDestinasiBinding
import com.warisan.kita.model.Destinasi

class DestinasiAdapter(
    private var listLoveLive: List<Destinasi>, private var listener: DestinasiAdapterListener
) : RecyclerView.Adapter<DestinasiAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(private val bind: ItemDestinasiBinding) :
        RecyclerView.ViewHolder(bind.root) {
        fun bindItem(destinasi: Destinasi) {
            with(bind) {
                tvDestinasi.text = destinasi.nama
            }
            Glide.with(itemView).load(destinasi.gambar).into(bind.ivDestinasi)
            itemView.setOnClickListener {
                listener.onClick(destinasi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapter =
            ItemDestinasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(adapter)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItem(listLoveLive[position])
    }

    override fun getItemCount(): Int = listLoveLive.size

    public interface DestinasiAdapterListener {
        fun onClick(destinasi: Destinasi)
    }
}