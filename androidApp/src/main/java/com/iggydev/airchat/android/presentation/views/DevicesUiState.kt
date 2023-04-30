package com.iggydev.airchat.android.presentation.views

import com.iggydev.airchat.android.connection.domain.BluetoothDeviceDomain

data class DevicesUiState(
    val pairedDevices: List<BluetoothDeviceDomain?> = emptyList(),
    val scannedDevices: List<BluetoothDeviceDomain?> = emptyList()
)
