package com.example.gotoit.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


//@Database(entities = [LikedEvents::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class AppDatabase: RoomDatabase() {
//
//    abstract fun eventsDao(): EventsDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = databaseBuilder(
//                    context = context.applicationContext,
//                    klass = AppDatabase::class.java,
//                    name = "app_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}