// ui/ServerPickerSheet.kt
// НАЗНАЧЕНИЕ: модальный список серверов (bottom sheet).
// ЧТО ДЕЛАЕТ: показывает все серверы, отмечает выбранный, обрабатывает тап.

package com.dgergeleziu.mdvpn.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgergeleziu.mdvpn.models.VpnServer
import com.dgergeleziu.mdvpn.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerPickerSheet(
    servers: List<VpnServer>,
    selected: VpnServer,
    onSelect: (VpnServer) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = AppColors.background,
        contentColor = AppColors.textPrimary
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
            Text(
                text = "Choose server",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.textPrimary
            )
            Spacer(Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(servers, key = { it.id }) { server ->
                    ServerRow(
                        server = server,
                        isSelected = server.id == selected.id,
                        onClick = { onSelect(server) }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ServerRow(
    server: VpnServer,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppColors.surface, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(server.flag, fontSize = 24.sp)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                server.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.textPrimary
            )
            Text(
                server.country,
                fontSize = 12.sp,
                color = AppColors.textSecondary
            )
        }
        Text(
            "${server.ping} ms",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (server.ping < 60) AppColors.success else AppColors.textSecondary
        )
        if (isSelected) {
            Spacer(Modifier.width(8.dp))
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = AppColors.accent,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
