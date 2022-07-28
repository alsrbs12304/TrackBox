package com.gyunni.trackbox.data

import androidx.lifecycle.LiveData
import com.gyunni.trackbox.data.model.Delivery
import com.gyunni.trackbox.data.retrofit.DeliveryService
import com.gyunni.trackbox.data.room.DeliveryDao

class DeliveryRepository(private val deliveryDao: DeliveryDao) {

    fun getList() : LiveData<MutableList<Delivery>> {
        return deliveryDao.getList()
    }

    fun insert(delivery: Delivery){
        deliveryDao.insert(delivery)
    }

    fun delete(delivery: Delivery){
        deliveryDao.delete(delivery)
    }

    fun update(delivery: Delivery){
        deliveryDao.update(delivery)
    }
}