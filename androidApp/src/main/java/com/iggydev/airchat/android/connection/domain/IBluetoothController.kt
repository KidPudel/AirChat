package com.iggydev.airchat.android.connection.domain

import android.bluetooth.BluetoothDevice
import kotlinx.coroutines.flow.StateFlow

interface IBluetoothController {
    // track devices
    val pairedDevices: StateFlow<List<BluetoothDeviceDomain?>>
    val scannedDevices: StateFlow<List<BluetoothDeviceDomain?>>

    fun startDiscovery()
    fun stopDiscovery()

    fun connectToDevice()

    fun closeConnection()
    fun release()

    fun queryPairedDevices()

    fun hasPermission(permission: String): Boolean
}