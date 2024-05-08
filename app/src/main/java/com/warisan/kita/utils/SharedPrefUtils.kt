package com.warisan.kita.utils

import android.content.Context

class SharedPrefUtils {
    companion object{
        public fun saveUserId(id:Int, ctx:Context){
            val sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("id", id)
            editor.apply()
        }
        public fun removeUserId(ctx:Context){
            val sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.remove("id")
            editor.apply()
        }
        public fun getId(ctx:Context):Int{
            val sharedPreferences = ctx.getSharedPreferences("user", Context.MODE_PRIVATE)
            return sharedPreferences.getInt("id",-1);
        }
    }
}