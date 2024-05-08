package com.warisan.kita.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warisan.kita.database.AppDatabase
import com.warisan.kita.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel: ViewModel() {
    private val _loginSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val loginSuccess:LiveData<Boolean> = _loginSuccess

    private val _errorLogin: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorLogin:LiveData<String> = _errorLogin

    private val _loading:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val loading:LiveData<Boolean> = _loading

    private val _isLogged:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val isLogged:LiveData<Boolean> = _isLogged

    public fun login(email:String,password:String,ctx: Context){
        _loading.value = true
        val db = AppDatabase.getInstance(ctx)
        GlobalScope.launch{
            withContext(Dispatchers.Main) {
                val user = db.userDao().login(email, password)
                user?.let {
                    SharedPrefUtils.saveUserId(it.uid!!,ctx)
                    _loginSuccess.value = true
                    Log.d("TAG", "WOW")
                }?:run {
                    _errorLogin.value = "Email atau kata sandi salah"
                }
                _loading.value = false
            }
        }
    }

    public fun checkLogin(ctx: Context) {
        val id = SharedPrefUtils.getId(ctx)
        _isLogged.value = id!=-1
    }
}