package com.gyunni.trackbox.view.ui.add

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gyunni.trackbox.data.model.Delivery
import com.gyunni.trackbox.data.DeliveryRepository
import com.gyunni.trackbox.data.model.DeliveryResponse
import com.gyunni.trackbox.di.viewModelModule
import com.gyunni.trackbox.view.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.ConnectException


class AddDeliveryViewModel(private val repository: DeliveryRepository) : BaseViewModel() {

    val carrierName = MutableLiveData<String>()
    val trackId = MutableLiveData<String>()
    val nickName = MutableLiveData<String>()

    fun insert(delivery: Delivery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(delivery)
        }
    }
}