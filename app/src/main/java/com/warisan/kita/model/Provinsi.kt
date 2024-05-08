package com.warisan.kita.model

import java.io.Serializable

@kotlinx.serialization.Serializable
data class Provinsi(
    val gambar:String,
    val nama:String,
    val destinasi:List<Destinasi>
):Serializable