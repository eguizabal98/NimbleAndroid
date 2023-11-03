package com.eem.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.eem.data.room.model.AccessTokenEntity

@Dao
interface AccessTokenDao {
    @Query("SELECT * FROM AccessTokenEntity")
    fun getAll(): List<AccessTokenDao>?

    @Insert
    fun insertAll(vararg accessTokenEntity: AccessTokenEntity)

    @Query("DELETE FROM AccessTokenEntity")
    fun clear()
}
