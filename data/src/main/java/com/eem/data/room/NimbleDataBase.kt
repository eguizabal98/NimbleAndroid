package com.eem.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eem.data.room.dao.AccessTokenDao
import com.eem.data.room.model.AccessTokenEntity

@Database(
    entities = [AccessTokenEntity::class],
    version = 1
)
abstract class NimbleDataBase : RoomDatabase() {
    abstract fun accessTokenDao(): AccessTokenDao
}
