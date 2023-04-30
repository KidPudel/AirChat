package com.iggydev.airchat.android.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iggydev.airchat.android.connection.data.BluetoothController
import com.iggydev.airchat.android.connection.domain.BluetoothDeviceDomain
import com.iggydev.airchat.android.connection.domain.IBluetoothController
import com.iggydev.airchat.android.presentation.views.DevicesUiState
import kotlinx.coroutines.flow.*

class BluetoothViewModel(
    private val bluetoothController: IBluetoothController
) : ViewModel() {

    private val _devicesState = MutableStateFlow(DevicesUiState())
    // combined flows to generate a new flow based on controller's flows
    val devicesState: StateFlow<DevicesUiState> = combine(
        bluetoothController.pairedDevices,
        bluetoothController.scannedDevices,
        _devicesState
    ) { paired, scanned, state ->
      state.copy(
          pairedDevices = paired,
          scannedDevices = scanned
      )

    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = _devicesState.value)


    fun startScanning() {
        bluetoothController.startDiscovery()
    }

    fun stopScanning() {
        bluetoothController.stopDiscovery()
    }
}