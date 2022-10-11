package com.example.jobsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobsapp.models.FavoriteJob

@Database(entities = [FavoriteJob::class], version = 1, exportSchema = false)
abstract class FavoriteJobDatabase : RoomDatabase() {

    abstract fun favDao() : FavoriteDao

    companion object {
        @Volatile
        private var instance : FavoriteJobDatabase? = null
        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: getDatabase(context).also { instance = it }
        }

        fun getDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,FavoriteJobDatabase::class.java,"myDb.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}