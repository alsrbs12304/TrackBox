package com.gyunni.trackbox.di

import com.gyunni.trackbox.view.ui.add.AddDeliveryViewModel
import com.gyunni.trackbox.view.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module{
    viewModel{MainViewModel(get())}
    viewModel{AddDeliveryViewModel(get())}
}