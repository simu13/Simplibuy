package com.example.simplibuy.di

import android.content.Context
import androidx.room.Room
import com.example.simplibuy.database.ShoppingDatabase
import com.example.simplibuy.others.Constants.RUNNING_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {
@Singleton
@Provides
    fun provideRunningDatabase(@ApplicationContext app : Context) = Room.databaseBuilder(
        app,
        ShoppingDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun getRunDao(db:ShoppingDatabase)=db.getRunDao()



}