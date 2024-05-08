package com.warisan.kita.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warisan.kita.model.Provinsi

class ProvinsiViewModel: ViewModel() {
    private val _listProvinsi: MutableLiveData<List<Provinsi>> by lazy {
        MutableLiveData<List<Provinsi>>()
    }
    public val listProvinsi:LiveData<List<Provinsi>> = _listProvinsi

    public fun setList(listProvinsi:List<Provinsi>){
        this._listProvinsi.value = listProvinsi
    }
}