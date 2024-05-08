package com.warisan.kita.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.warisan.kita.database.AppDatabase
import com.warisan.kita.database.User
import com.warisan.kita.model.Provinsi
import com.warisan.kita.utils.SharedPrefUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel: ViewModel() {
    private val _user: MutableLiveData<User> by lazy {
        MutableLiveData<User>()
    }
    public val user:LiveData<User> = _user

    private val _deleteAccountSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val deleteAccountSuccess:LiveData<Boolean> = _deleteAccountSuccess

    private val _logoutSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val logoutSuccess:LiveData<Boolean> = _logoutSuccess

    private val _loading:MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    public val loading:LiveData<Boolean> = _loading

    public fun loadUser(ctx:Context){
        val db = AppDatabase.getInstance(ctx)
        val id = SharedPrefUtils.getId(ctx)
        GlobalScope.launch{
            withContext(Dispatchers.Main) {
                val user = db.userDao().getUser(id)
                user?.let {
                    _user.value = it
                }
            }
        }
    }

    public fun logout(ctx:Context){
        SharedPrefUtils.removeUserId(ctx)
        _logoutSuccess.value = true
    }

    public fun deleteAccount(ctx:Context){
        _loading.value = true
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                user.value?.let {
                    val db = AppDatabase.getInstance(ctx)
                    db.userDao().delete(it)
                    SharedPrefUtils.removeUserId(ctx)
                    _deleteAccountSuccess.value = true
                }
                _loading.value = false
            }
        }
    }
}