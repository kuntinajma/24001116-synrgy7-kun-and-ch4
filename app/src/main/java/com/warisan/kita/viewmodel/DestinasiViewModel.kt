package com.warisan.kita.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warisan.kita.model.Provinsi

class DestinasiViewModel: ViewModel() {
    private val _provinsi: MutableLiveData<Provinsi> by lazy {
        MutableLiveData<Provinsi>()
    }
    public val provinsi:LiveData<Provinsi> = _provinsi

    public fun setProvinsi(provinsi:Provinsi){
        this._provinsi.value = provinsi
    }
}