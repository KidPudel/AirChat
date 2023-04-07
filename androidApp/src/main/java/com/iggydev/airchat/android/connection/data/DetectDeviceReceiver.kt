package com.iggydev.airchat.android.connection.data

import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class DetectDeviceReceiver(
    val onDeviceFound: (BluetoothDevice?) -> Unit
) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            BluetoothDevice.ACTION_FOUND -> {
                val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    intent.getParcelableExtra(BluetoothDevice.ACTION_FOUND, BluetoothDevice::class.java)
                } else {
                    intent.getParcelableExtra(BluetoothDevice.ACTION_FOUND)
                }
                onDeviceFound.invoke(device)
            }
        }
    }
}