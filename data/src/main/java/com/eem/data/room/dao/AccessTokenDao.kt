package com.eem.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eem.data.room.model.AccessTokenEntity

@Dao
interface AccessTokenDao {
    @Query("SELECT * FROM AccessTokenEntity")
    suspend fun getAll(): List<AccessTokenEntity>?

    @Insert
    suspend fun insertAll(vararg accessTokenEntity: AccessTokenEntity)

    @Query("DELETE FROM AccessTokenEntity")
    suspend fun clear()
}
