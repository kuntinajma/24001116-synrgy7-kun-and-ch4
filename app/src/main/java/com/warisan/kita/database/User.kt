package com.warisan.kita.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid:Int?=null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "nickname") val nickname: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") var password: String
)