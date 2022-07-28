package com.gyunni.trackbox.di

import androidx.room.Room
import com.gyunni.trackbox.data.room.DeliveryDatabase
import org.koin.android.ext.koin.androidApplication
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