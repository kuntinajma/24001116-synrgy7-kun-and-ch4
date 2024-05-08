package com.warisan.kita.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warisan.kita.database.AppDatabase
import com.warisan.kita.database.User
import com.warisan.kita.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UbahKataSandiViewModel: ViewModel() {
    private val _changePasswordSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val changePasswordSuccess:LiveData<Boolean> = _changePasswordSuccess

    private val _errorOldPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorOldPassword:LiveData<String> = _errorOldPassword

    private val _errorPassword: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    public val errorPassword:LiveData<String> = _errorPassword

    private val _loading:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val loading:LiveData<Boolean> = _loading
    
    public fun changePassword(oldPassword: String, password:String, ctx:Context){
        _loading.value = true
        val db = AppDatabase.getInstance(ctx)
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                if(isValid(oldPassword,password,ctx)) {
                    val id = SharedPrefUtils.getId(ctx)

                    val user = db.userDao().getUser(id)
                    user?.password = password
                    user?.let {
                        it.password = password
                        db.userDao().update(it)
                        _changePasswordSuccess.value = true
                        Log.d("LOG", "changePassword: ")
                    }
                }

                _loading.value = false
            }
        }

    }

    private suspend fun isValid(oldPassword:String, password:String, ctx:Context): Boolean {

        var valid = true

        if (oldPassword.isEmpty()) {
            _errorOldPassword.value = "Kata sandi lama tidak boleh kosong"
            valid = false
        }else{
            val id = SharedPrefUtils.getId(ctx)

            val db = AppDatabase.getInstance(ctx)
            val user = db.userDao().getUser(id)
            user?.let {
                if (user.password != oldPassword) {
                    _errorOldPassword.value = "Kata sandi lama salah"
                    valid = false
                } else {
                    _errorOldPassword.value = ""
                }
            }
            Log.d("TAG", "isValidScoop : "+valid)
        }

        if(password.isEmpty()){
            _errorPassword.value = "Password tidak boleh kosong"
            valid = false
        }else{
            _errorPassword.value = ""
        }
        Log.d("TAG", "isValid: "+valid)
        return valid
    }
}