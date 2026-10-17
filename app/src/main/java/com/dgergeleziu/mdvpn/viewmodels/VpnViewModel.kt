// viewmodels/VpnViewModel.kt
// НАЗНАЧЕНИЕ: связка UI ↔ Services.
// ЧТО ДЕЛАЕТ: хранит состояние в StateFlow, обрабатывает действия.
// ЗАЧЕМ StateFlow: аналог @Published в SwiftUI — Compose сам перерисуется.

package com.dgergeleziu.mdvpn.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgergeleziu.mdvpn.models.VpnServer
import com.dgergeleziu.mdvpn.models.VpnStatus
import com.dgergeleziu.mdvpn.models.sampleServers
import com.dgergeleziu.mdvpn.services.MockVpnService
import com.dgergeleziu.mdvpn.services.VpnServiceInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VpnViewModel : ViewModel() {

    // "Розетка": здесь сегодня MockVpnService, завтра RealVpnService.
    private val service: VpnServiceInterface = MockVpnService()

    // _status — изменяем изнутри, status — только для чтения снаружи.
    private val _status = MutableStateFlow<VpnStatus>(VpnStatus.Disconnected)
    val status: StateFlow<VpnStatus> = _status.asStateFlow()

    private val _servers = MutableStateFlow(sampleServers)
    val servers: StateFlow<List<VpnServer>> = _servers.asStateFlow()

    private val _selectedServer = MutableStateFlow(sampleServers[0])
    val selectedServer: StateFlow<VpnServer> = _selectedServer.asStateFlow()

    init {
        // Подписываемся на изменения статуса от сервиса.
        service.statusListener = { newStatus ->
            _status.value = newStatus
        }
    }

    /** Сменить выбранный сервер. */
    fun selectServer(server: VpnServer) {
        _selectedServer.value = server
    }

    /** Один вход для кнопки Connect. */
    fun toggleConnection() {
        viewModelScope.launch {
            when (_status.value) {
                is VpnStatus.Disconnected, is VpnStatus.Error ->
                    service.connect(_selectedServer.value)
                is VpnStatus.Connected ->
                    service.disconnect()
                else -> Unit  // connecting / disconnecting — игнорируем
            }
        }
    }
}
