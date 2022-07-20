package com.gyunni.trackbox

import android.app.Application
import androidx.lifecycle.LiveData

class DeliveryRepository(application: Application) {

    private val deliveryDao = DeliveryDatabase.getInstance(application)!!.deliveryDao()

    suspend fun insert(delivery: Delivery){
        deliveryDao.insert(delivery)
    }

    fun getList() : LiveData<List<Delivery>> {
        return deliveryDao.getList()
    }

    companion object {
        private var instance: DeliveryRepository? = null

        fun getInstance(application : Application): DeliveryRepository? { // singleton pattern
            if (instance == null) instance = DeliveryRepository(application)
            return instance
        }
    }
}