package com.gyunni.trackbox.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gyunni.trackbox.data.model.Delivery

@Dao
interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(delivery: Delivery)

    @Query("SELECT * FROM delivery")
    fun getList() : LiveData<MutableList<Delivery>>

    @Delete
    fun delete(delivery: Delivery)

    @Update
    fun update(delivery: Delivery)
}