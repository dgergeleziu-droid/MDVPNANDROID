// services/MockVpnService.kt
// НАЗНАЧЕНИЕ: заглушка, имитирующая VPN.
// ЧТО ДЕЛАЕТ: спит 1.2с при подключении и 0.6с при отключении, шлёт статусы.
// ЗАЧЕМ: чтобы верстать UI и тестировать состояния без реального сервера.

package com.dgergeleziu.mdvpn.services

import com.dgergeleziu.mdvpn.models.VpnServer
import com.dgergeleziu.mdvpn.models.VpnStatus
import kotlinx.coroutines.delay

class MockVpnService : VpnServiceInterface {

    override var statusListener: ((VpnStatus) -> Unit)? = null

    override suspend fun connect(server: VpnServer) {
        // 1. Сообщаем: начали.
        statusListener?.invoke(VpnStatus.Connecting)

        // 2. Имитация задержки сети.
        delay(1200)

        // 3. Сервер с хостом *.bad — для теста ошибки.
        if (server.host.endsWith(".bad")) {
            statusListener?.invoke(VpnStatus.Error("Не удалось подключиться к ${server.name}"))
            return
        }

        // 4. Успех: имя + время старта.
        statusListener?.invoke(VpnStatus.Connected(server.name, System.currentTimeMillis()))
    }

    override suspend fun disconnect() {
        statusListener?.invoke(VpnStatus.Disconnecting)
        delay(600)
        statusListener?.invoke(VpnStatus.Disconnected)
    }
}
