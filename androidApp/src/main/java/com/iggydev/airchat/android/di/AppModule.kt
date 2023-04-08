package com.iggydev.airchat.android.di

import android.app.Application
import com.iggydev.airchat.android.connection.data.BluetoothController
import com.iggydev.airchat.android.connection.domain.IBluetoothController
import org.koin.dsl.module

val appModule = module {
    single<IBluetoothController> {
        BluetoothController(Application())
    }
}