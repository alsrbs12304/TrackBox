package com.gyunni.trackbox.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gyunni.trackbox.data.model.Delivery

@Database(entities = [Delivery::class], version = 2, exportSchema = false)
abstract class DeliveryDatabase : RoomDatabase() {

    abstract fun deliveryDao() : DeliveryDao

}