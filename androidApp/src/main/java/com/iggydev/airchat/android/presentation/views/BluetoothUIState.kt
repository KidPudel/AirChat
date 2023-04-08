package com.iggydev.airchat.android.presentation.views

import com.iggydev.airchat.android.connection.domain.BluetoothDeviceDomain
import kotlinx.coroutines.flow.StateFlow

data class BluetoothUIState(
    val pairedDevices: List<BluetoothDeviceDomain?> = emptyList(),
    val scannedDevices: List<BluetoothDeviceDomain?> = emptyList()
)
