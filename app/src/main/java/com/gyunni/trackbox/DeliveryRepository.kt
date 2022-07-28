package com.gyunni.trackbox

import android.app.Application
import androidx.lifecycle.LiveData

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
}