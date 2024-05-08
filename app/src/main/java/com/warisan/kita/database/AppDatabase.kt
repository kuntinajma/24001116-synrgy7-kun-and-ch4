package com.warisan.kita.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object{
        private var db:AppDatabase? = null
        fun getInstance(ctx:Context):AppDatabase{
            if(db==null) {
                db = Room.databaseBuilder(
                    ctx,
                    AppDatabase::class.java, "warisan-kita"
                ).build()
            }
            return db!!
        }
    }
}