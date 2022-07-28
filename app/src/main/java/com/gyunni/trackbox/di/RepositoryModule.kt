package com.gyunni.trackbox.di

import com.gyunni.trackbox.data.DeliveryRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        DeliveryRepository(get())
    }
}