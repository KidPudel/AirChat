package com.iggydev.airchat.android.connection.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import com.iggydev.airchat.android.connection.domain.BluetoothDeviceDomain
import com.iggydev.airchat.android.connection.domain.IBluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

// so android won't complain 🙃
@SuppressLint("MissingPermission")
class BluetoothController(
    private val context: Context
) : IBluetoothController {
    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDevice>>
        get() = _pairedDevices as StateFlow<List<BluetoothDevice>>
    override val scannedDevices: StateFlow<List<BluetoothDevice>>
        get() = TODO("Not yet implemented")


    override fun startDiscovery() {
        if (hasPermission(permission = Manifest.permission.BLUETOOTH_SCAN)) {
            bluetoothAdapter?.startDiscovery()
        }

    }

    override fun stopDiscovery() {
        TODO("Not yet implemented")
    }

    override fun connectToDevice() {
        TODO("Not yet implemented")
    }

    override fun closeConnection() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    private fun updatePairedDevices() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter?.bondedDevices
            ?.map { bluetoothDevice -> bluetoothDevice.toBluetoothDeviceDomain() }
            ?.also { newDevices-> _pairedDevices.update {newDevices} }
    }

    override fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}