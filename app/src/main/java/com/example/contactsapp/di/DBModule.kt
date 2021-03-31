package com.example.contactsapp.di

import android.content.Context
import com.example.contactsapp.data.Repository
import com.example.contactsapp.data.local.ContactDao
import com.example.contactsapp.data.local.ContactRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = ContactRoomDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideFeaturesDao(contactRoomDatabase: ContactRoomDatabase): ContactDao {
        return contactRoomDatabase.contactDao()
    }

    @Provides
    @Singleton
    fun provideRepository( dao: ContactDao) = Repository( dao )
}