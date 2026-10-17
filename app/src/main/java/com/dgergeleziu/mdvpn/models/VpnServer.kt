// models/VpnServer.kt
// НАЗНАЧЕНИЕ: модель одного VPN-сервера.
// ЧТО ДЕЛАЕТ: хранит имя, страну, флаг, адрес, ping.
// ЗАЧЕМ data class: автоматически даёт equals/hashCode/copy.

package com.dgergeleziu.mdvpn.models

import java.util.UUID

data class VpnServer(
    val id: String = UUID.randomUUID().toString(),  // уникальный id для списка
    val name: String,                                // "Amsterdam #1"
    val country: String,                             // "Netherlands"
    val flag: String,                                // "🇳🇱"
    val host: String,                                // "nl1.mdvpn.net"
    val ping: Int                                    // 24
)

// Демо-данные — пока нет реального API.
val sampleServers: List<VpnServer> = listOf(
    VpnServer(name = "Amsterdam #1", country = "Netherlands", flag = "🇳🇱", host = "nl1.mdvpn.net", ping = 24),
    VpnServer(name = "Frankfurt #2",  country = "Germany",     flag = "🇩🇪", host = "de2.mdvpn.net", ping = 31),
    VpnServer(name = "New York #1",   country = "USA",         flag = "🇺🇸", host = "us1.mdvpn.net", ping = 92),
    VpnServer(name = "Tokyo #3",      country = "Japan",       flag = "🇯🇵", host = "jp3.mdvpn.net", ping = 140)
)
