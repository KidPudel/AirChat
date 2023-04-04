package com.iggydev.airchat.android.connection.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import com.iggydev.airchat.android.connection.domain.BluetoothDeviceDomain
import com.iggydev.airchat.android.connection.domain.IBluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.LinkedList

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
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _pairedDevices.asStateFlow()

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _scannedDevices.asStateFlow()

    val fetchDeviceReceiver = DetectDeviceReceiver(onDetectDevice = { detectedDevice ->
        // find new devices that is not scanned yet
        _scannedDevices.update { devices ->
            val newDevice = detectedDevice.toBluetoothDeviceDomain()
            if (newDevice in devices) devices else devices + newDevice
        }
    })


    init {
        queryPairedDevices()
    }

    override fun startDiscovery() {
        if (!hasPermission(permission = Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }
        queryPairedDevices()

        // structured description of intent that can match against actions categories and data
        val foundFilter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        context.registerReceiver(fetchDeviceReceiver, foundFilter)
        bluetoothAdapter?.startDiscovery()


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

    private fun queryPairedDevices() {
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