package com.warisan.kita.viewmodel

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.warisan.kita.R
import com.warisan.kita.database.AppDatabase
import com.warisan.kita.database.User
import com.warisan.kita.model.Provinsi
import com.warisan.kita.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterViewModel: ViewModel() {
    private val _registerSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val registerSuccess:LiveData<Boolean> = _registerSuccess

    private val _errorName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorName:LiveData<String> = _errorName

    private val _errorNickname: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorNickname:LiveData<String> = _errorNickname

    private val _errorEmail: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorEmail:LiveData<String> = _errorEmail

    private val _errorPhone: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorPhone:LiveData<String> = _errorPhone

    private val _errorPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorPassword:LiveData<String> = _errorPassword

    private val _loading:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val loading:LiveData<Boolean> = _loading

    public fun register(name:String, nickname:String, email:String, phone: String, password:String, ctx:Context){
        _loading.value = true
        val db = AppDatabase.getInstance(ctx)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                if(isValid(name,nickname,email,phone,password,ctx)) {
                    val user = User(
                        name = name,
                        nickname = nickname,
                        email = email,
                        phone = phone,
                        password = password
                    );
                    val uid = db.userDao().insert(user)
                    SharedPrefUtils.saveUserId(uid.toInt(), ctx)
                    _registerSuccess.value = true
                }
                _loading.value = false
            }
        }
    }

    private suspend fun isValid(name:String, nickname:String, email:String, phone: String, password:String, ctx:Context): Boolean {
        var valid = true
        
        if (name.isEmpty()) {
            _errorName.value = "Nama tidak boleh kosong"
            valid = false
        } else {
            _errorName.value = ""
        }

        if (nickname.isEmpty()) {
            _errorNickname.value = "Nama panggilan tidak boleh kosong"
            valid = false
        } else {
            _errorName.value = ""
        }

        if (email.isEmpty()) {
            _errorEmail.value = "Email tidak boleh kosong"
            valid = false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            _errorEmail.value = "Email tidak valid"
            valid = false
        }else{
            val db = AppDatabase.getInstance(ctx)
            val user = db.userDao().getUser(email)
            user?.let {
                _errorEmail.value = "Email sudah terpakai"
                valid = false
            }?:run {
                _errorEmail.value = ""
            }
        }

        if(phone.isEmpty()){
            _errorPhone.value = "Nomor telepon tidak boleh kosong"
            valid = false
        }else{
            _errorPhone.value = ""
        }

        if(password.isEmpty()){
            _errorPassword.value = "Password tidak boleh kosong"
            valid = false
        }else{
            _errorPassword.value = ""
        }
        return valid
    }
}