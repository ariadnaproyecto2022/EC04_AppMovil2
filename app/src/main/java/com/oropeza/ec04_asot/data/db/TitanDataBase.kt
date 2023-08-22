package com.oropeza.ec04_asot.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oropeza.ec04_asot.model.AttackOnTitan

@Database(entities = [AttackOnTitan::class], version = 1)
abstract class TitanDataBase: RoomDatabase() {
    abstract fun titanDao(): TitanDao

    companion object {
        @Volatile
        private var instance: TitanDataBase? = null
        fun getDatabase(context: Context): TitanDataBase {
            if (instance == null) {
                synchronized(this) {
                    instance = builDatabase(context)
                }
            }
            return instance!!
        }
        private fun builDatabase(context: Context): TitanDataBase? {
            return Room.databaseBuilder(
                context.applicationContext,
                TitanDataBase::class.java,
                "titan_database"
            ).build()
        }
    }
}