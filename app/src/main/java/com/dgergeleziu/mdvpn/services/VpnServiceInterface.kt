// services/VpnServiceInterface.kt
// НАЗНАЧЕНИЕ: контракт между UI и реальным VPN-движком ("розетка").
// ЧТО ДЕЛАЕТ: описывает методы, которые должна реализовать любая реализация.
// ЗАЧЕМ ИНТЕРФЕЙС: завтра заменим MockVpnService на RealVpnService,
//                 UI и ViewModel не тронем.

package com.dgergeleziu.mdvpn.services

import com.dgergeleziu.mdvpn.models.VpnServer
import com.dgergeleziu.mdvpn.models.VpnStatus

interface VpnServiceInterface {

    /** Слушатель изменений статуса. ViewModel подписывается. */
    var statusListener: ((VpnStatus) -> Unit)?

    /** Подключиться к серверу. suspend — длительная операция. */
    suspend fun connect(server: VpnServer)

    /** Отключиться. */
    suspend fun disconnect()
}
