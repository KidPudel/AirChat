package com.iggydev.airchat.android.presentation.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iggydev.airchat.android.connection.domain.IBluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


class BluetoothViewModel(
    private val bluetoothController: IBluetoothController
) : ViewModel() {
    private val _bluetoothState = MutableStateFlow(BluetoothUIState())

    // fill the bluetooth state from bluetooth controller
    val bluetoothState = combine(
        bluetoothController.pairedDevices,
        bluetoothController.scannedDevices,
        _bluetoothState
    ) { paired, scanned, state ->
        state.copy(
            pairedDevices = paired,
            scannedDevices = scanned
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _bluetoothState.value)

    fun startDiscovery() {
        bluetoothController.startDiscovery()
    }

    fun stopDiscovery() {
        bluetoothController.stopDiscovery()
    }
}