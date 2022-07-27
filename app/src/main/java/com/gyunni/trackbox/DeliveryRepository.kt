package com.gyunni.trackbox

import android.app.Application
import androidx.lifecycle.LiveData

class DeliveryRepository(application: Application) {

    private val deliveryDao = DeliveryDatabase.getInstance(application)!!.deliveryDao()

    fun getList() : LiveData<MutableList<Delivery>> {
        return deliveryDao.getList()
    }

    fun insert(delivery: Delivery){
        deliveryDao.insert(delivery)
    }

    fun delete(delivery: Delivery){
        deliveryDao.delete(delivery)
    }
    companion object {
        private var instance: DeliveryRepository? = null

        fun getInstance(application : Application): DeliveryRepository? { // singleton pattern
            if (instance == null) instance = DeliveryRepository(application)
            return instance
        }
    }
}