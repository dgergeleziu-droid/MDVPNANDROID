// ui/theme/Theme.kt
// НАЗНАЧЕНИЕ: цвета в одном месте.
// ЗАЧЕМ: менять палитру в одном файле, а не искать по всем экранам.

package com.dgergeleziu.mdvpn.ui.theme

import androidx.compose.ui.graphics.Color

object AppColors {
    val background    = Color(0xFF0E1116)
    val surface       = Color(0xFF161A21)
    val surfaceHigh   = Color(0xFF1E232B)
    val textPrimary   = Color(0xFFE8ECF1)
    val textSecondary = Color(0xFF8A929C)
    val accent        = Color(0xFF4F8CFF)
    val danger        = Color(0xFFFF5A5A)
    val success       = Color(0xFF36D399)
    val logo          = Color(0xFF59616B)
}
