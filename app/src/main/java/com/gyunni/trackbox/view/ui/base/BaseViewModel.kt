package com.gyunni.trackbox.view.ui.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

abstract class BaseViewModel : ViewModel() {

    open val isLoading = MutableLiveData(View.INVISIBLE)
    open fun showLoading() = isLoading.postValue(View.VISIBLE)
    open fun hideLoading() = isLoading.postValue(View.INVISIBLE)

    suspend fun <T> handle(call: suspend () -> T): T? {
        return withContext(CoroutineScope(coroutineContext).coroutineContext) {
            call.runCatching { this.invoke() }
                .getOrElse {
                    null
                }
        }
    }
}