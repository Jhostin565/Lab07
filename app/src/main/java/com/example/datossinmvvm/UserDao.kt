package com.example.datossinmvvm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM User ORDER BY uid DESC LIMIT 1")
    suspend fun getLastUser(): User?

    @Query("DELETE FROM User WHERE uid = :userId")
    suspend fun deleteUserById(userId: Int)

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM User")
    suspend fun getAll(): List<User>
}

