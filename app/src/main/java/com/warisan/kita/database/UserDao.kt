package com.warisan.kita.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid = :userId")
    suspend fun getUser(userId: Int): User?

    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUser(email: String): User?

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?

    @Insert
    suspend fun insert(user: User):Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}