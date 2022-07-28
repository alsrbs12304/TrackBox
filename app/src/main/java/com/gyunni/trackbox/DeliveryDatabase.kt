package com.gyunni.trackbox

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Delivery::class], version = 2, exportSchema = false)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun deliveryDao() : DeliveryDao

}