package com.gyunni.trackbox.di

import androidx.room.Room
import com.gyunni.trackbox.DeliveryDatabase
import com.gyunni.trackbox.DeliveryRepository
import com.gyunni.trackbox.view.ui.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val roomModule = module {
    single {
        Room.databaseBuilder(androidApplication(), DeliveryDatabase::class.java, "delivery_database")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<DeliveryDatabase>().deliveryDao()
    }
}