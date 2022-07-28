package com.gyunni.trackbox.view.ui.add

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gyunni.trackbox.Delivery
import com.gyunni.trackbox.DeliveryRepository
import com.gyunni.trackbox.view.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddDeliveryViewModel(private val repository: DeliveryRepository) : BaseViewModel() {

    val carrierName = MutableLiveData<String>()

    fun insert(delivery: Delivery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(delivery)
        }
    }
}
