package com.iggydev.airchat.android.connection.data

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

// get devices
class DetectDeviceReceiver(
    val onDetectDevice: (BluetoothDevice) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        when (action) {
            BluetoothDevice.ACTION_FOUND -> {
                // get the device that is found
                val detectedDevice: BluetoothDevice? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(
                        BluetoothDevice.EXTRA_DEVICE,
                        BluetoothDevice::class.java
                    )
                } else {
                    intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                }
                // logic for detected devices
                onDetectDevice(detectedDevice!!)
            }
        }
    }
}