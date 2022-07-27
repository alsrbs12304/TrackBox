package com.gyunni.trackbox.view.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gyunni.trackbox.Delivery
import com.gyunni.trackbox.DeliveryRepository
import com.gyunni.trackbox.view.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: DeliveryRepository) : BaseViewModel(){

    fun getList() : LiveData<MutableList<Delivery>> {
        return repository.getList()
    }

    fun delete(delivery: Delivery){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(delivery)
        }
    }

    class Factory(private val application: Application) :
        ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(DeliveryRepository.getInstance(application)!!) as T
        }
    }
}