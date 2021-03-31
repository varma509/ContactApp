package com.example.contactsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contactsapp.data.Contact
import com.example.contactsapp.util.DATABASE_NAME

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactRoomDatabase : RoomDatabase(){

    abstract fun contactDao(): ContactDao
    companion object {
        @Volatile private var instance: ContactRoomDatabase? = null

        fun getDatabase(context: Context): ContactRoomDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, ContactRoomDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}