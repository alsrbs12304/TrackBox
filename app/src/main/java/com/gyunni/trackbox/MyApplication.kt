package com.gyunni.trackbox

import android.app.Application
import com.gyunni.trackbox.di.networkModule
import com.gyunni.trackbox.di.repositoryModule
import com.gyunni.trackbox.di.roomModule
import com.gyunni.trackbox.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApplication)
            modules(
                listOf(
                    networkModule,
                    roomModule,
                    viewModelModule,
                    repositoryModule
                )
            )
        }
    }
}