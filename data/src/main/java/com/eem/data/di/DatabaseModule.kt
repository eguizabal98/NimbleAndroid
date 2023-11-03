package com.eem.data.di

import android.content.Context
import androidx.room.Room
import com.eem.data.room.NimbleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DB_NAME = "Nimble.db"

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): NimbleDataBase {
        return Room.databaseBuilder(
            context,
            NimbleDataBase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAccessTokenDao(database: NimbleDataBase) = database.accessTokenDao()
}
