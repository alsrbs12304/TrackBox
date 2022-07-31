package com.gyunni.trackbox.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gyunni.trackbox.data.model.Delivery
import com.gyunni.trackbox.data.DeliveryRepository
import com.gyunni.trackbox.view.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DeliveryRepository) : BaseViewModel(){

    val deliveryList =  MutableLiveData<Delivery>()

    fun getList() : LiveData<MutableList<Delivery>> {
        return repository.getList()
    }

    fun delete(delivery: Delivery){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(delivery)
        }
    }
}