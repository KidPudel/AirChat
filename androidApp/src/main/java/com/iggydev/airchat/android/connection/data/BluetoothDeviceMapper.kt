package com.iggydev.airchat.android.connection.data

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.iggydev.airchat.android.connection.domain.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}