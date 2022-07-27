package com.gyunni.trackbox

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DeliveryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(delivery: Delivery)

    @Query("SELECT * FROM delivery")
    fun getList() : LiveData<MutableList<Delivery>>

    @Delete
    fun delete(delivery: Delivery)
}