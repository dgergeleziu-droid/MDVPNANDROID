// ui/ConnectButton.kt
// НАЗНАЧЕНИЕ: большая круглая кнопка Connect/Disconnect.
// ЧТО ДЕЛАЕТ: цвет и иконка зависят от статуса, пульсирует при подключении.

package com.dgergeleziu.mdvpn.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgergeleziu.mdvpn.models.VpnStatus
import com.dgergeleziu.mdvpn.ui.theme.AppColors

@Composable
fun ConnectButton(
    status: VpnStatus,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isConnected = status is VpnStatus.Connected
    val isBusy = status is VpnStatus.Connecting || status is VpnStatus.Disconnecting

    // Пульсирующее кольцо при connecting.
    val transition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by transition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(tween(1400), RepeatMode.Restart),
        label = "pulseScale"
    )
    val pulseAlpha by transition.animateFloat(
        initialValue = 0.9f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(1400), RepeatMode.Restart),
        label = "pulseAlpha"
    )

    // Плавная смена цвета фона.
    val bgColor by animateColorAsState(
        targetValue = if (isConnected) AppColors.accent else AppColors.surfaceHigh,
        label = "bgColor"
    )

    Box(
        modifier = modifier.size(240.dp),
        contentAlignment = Alignment.Center
    ) {
        // Пульсирующее кольцо.
        if (status is VpnStatus.Connecting) {
            Box(
                modifier = Modifier
                    .size(210.dp)
                    .scale(pulseScale)
                    .alpha(pulseAlpha)
                    .border(width = 2.dp, color = AppColors.accent.copy(alpha = 0.5f), shape = CircleShape)
            )
        }

        // Основной круг.
        Box(
            modifier = Modifier
                .size(210.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = if (isConnected)
                            listOf(AppColors.accent, AppColors.accent.copy(alpha = 0.7f))
                        else
                            listOf(AppColors.surfaceHigh, AppColors.surface)
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 2.dp,
                    color = if (isConnected) AppColors.accent.copy(alpha = 0.9f) else AppColors.surfaceHigh,
                    shape = CircleShape
                )
                .clickable(enabled = !isBusy) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = when (status) {
                        is VpnStatus.Connected -> Icons.Default.Lock
                        is VpnStatus.Connecting, is VpnStatus.Disconnecting -> Icons.Default.Refresh
                        else -> Icons.Default.PowerSettingsNew
                    },
                    contentDescription = null,
                    tint = AppColors.textPrimary,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = when (status) {
                        is VpnStatus.Connected -> "Disconnect"
                        is VpnStatus.Connecting -> "Connecting…"
                        is VpnStatus.Disconnecting -> "Stopping…"
                        else -> "Connect"
                    },
                    color = AppColors.textPrimary,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
