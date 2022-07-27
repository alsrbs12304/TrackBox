package com.gyunni.trackbox

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Delivery::class], version = 2, exportSchema = false)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun deliveryDao() : DeliveryDao

    companion object {

        @Volatile
        private var instance: DeliveryDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DeliveryDatabase? {
            if (instance == null) {
                synchronized(DeliveryDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DeliveryDatabase::class.java,
                        "delivery_database"
                    )
                        .addMigrations(MIGRATION_1_2)
                        .build()
                }
            }
            return instance
        }
        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE 'delivery' ADD COLUMN 'nickName' TEXT")
            }
        }
    }
}