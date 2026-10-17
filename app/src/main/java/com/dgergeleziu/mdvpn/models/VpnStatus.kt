// models/VpnStatus.kt
// НАЗНАЧЕНИЕ: описать все состояния подключения одним типом.
// ЧТО ДЕЛАЕТ: sealed class (аналог sealed/enum в Swift) с данными внутри.
// ЗАЧЕМ SEALED CLASS: компилятор проверит, что when покрывает все ветки.

package com.dgergeleziu.mdvpn.models

sealed class VpnStatus {

    /** Ничего не делаем. */
    data object Disconnected : VpnStatus()

    /** Идёт подключение. */
    data object Connecting : VpnStatus()

    /** Подключено. server — имя сервера, since — время старта (мс). */
    data class Connected(val server: String, val since: Long) : VpnStatus()

    /** Идёт отключение. */
    data object Disconnecting : VpnStatus()

    /** Ошибка с текстом для пользователя. */
    data class Error(val message: String) : VpnStatus()

    /** Заголовок для статус-бара. */
    val title: String
        get() = when (this) {
            is Disconnected  -> "Not connected"
            is Connecting    -> "Connecting…"
            is Connected     -> "Connected"
            is Disconnecting -> "Disconnecting…"
            is Error         -> "Error"
        }
}
