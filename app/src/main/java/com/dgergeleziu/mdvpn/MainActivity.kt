// MainActivity.kt
// НАЗНАЧЕНИЕ: точка входа + главный экран.

package com.dgergeleziu.mdvpn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dgergeleziu.mdvpn.models.VpnStatus
import com.dgergeleziu.mdvpn.ui.ConnectButton
import com.dgergeleziu.mdvpn.ui.MdLogoBar
import com.dgergeleziu.mdvpn.ui.ServerPickerSheet
import com.dgergeleziu.mdvpn.ui.theme.AppColors
import com.dgergeleziu.mdvpn.viewmodels.VpnViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(vm: VpnViewModel = viewModel()) {
    // Подписываемся на все состояния из ViewModel.
    val status by vm.status.collectAsState()
    val servers by vm.servers.collectAsState()
    val selected by vm.selectedServer.collectAsState()

    // Флаг показа sheet — локальное состояние UI.
    var showPicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Логотип M&D справа сверху.
            MdLogoBar()

            Spacer(Modifier.height(8.dp))

            Spacer(Modifier.weight(1f))  // растяжка

            StatusBlock(status = status, country = selected.country)

            Spacer(Modifier.height(28.dp))

            // Кнопка Connect.
            ConnectButton(
                status = status,
                onClick = { vm.toggleConnection() }
            )

            Spacer(Modifier.height(28.dp))

            // Чип текущего сервера.
            ServerChip(
                server = selected,
                onClick = { showPicker = true }
            )

            Spacer(Modifier.weight(1f))  // растяжка

            Text(
                text = "M&D VPN · Secure tunnel",
                color = AppColors.textSecondary.copy(alpha = 0.7f),
                fontSize = 11.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // Sheet выбора сервера.
        if (showPicker) {
            ServerPickerSheet(
                servers = servers,
                selected = selected,
                onSelect = { vm.selectServer(it); showPicker = false },
                onDismiss = { showPicker = false }
            )
        }
    }
}

@Composable
private fun StatusBlock(status: VpnStatus, country: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = status.title,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.textPrimary
        )
        when (val s = status) {
            is VpnStatus.Connected -> SessionTimer(since = s.since)
            is VpnStatus.Error -> Text(
                text = s.message,
                fontSize = 13.sp,
                color = AppColors.danger
            )
            else -> Text(
                text = country,
                fontSize = 13.sp,
                color = AppColors.textSecondary
            )
        }
    }
}

@Composable
private fun SessionTimer(since: Long) {
    var now by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(since) {
        while (true) {
            now = System.currentTimeMillis()
            delay(1000)
        }
    }
    val elapsed = (now - since) / 1000
    val h = elapsed / 3600
    val m = (elapsed % 3600) / 60
    val s = elapsed % 60
    Text(
        text = "Session · %02d:%02d:%02d".format(h, m, s),
        fontSize = 13.sp,
        color = AppColors.textSecondary
    )
}

@Composable
private fun ServerChip(server: com.dgergeleziu.mdvpn.models.VpnServer, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .background(AppColors.surface, CircleShape)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(server.flag, fontSize = 18.sp)
        Spacer(Modifier.width(10.dp))
        Column {
            Text(
                server.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.textPrimary
            )
            Text(
                "${server.ping} ms",
                fontSize = 11.sp,
                color = AppColors.textSecondary
            )
        }
        Spacer(Modifier.width(8.dp))
        Icon(
            Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = AppColors.textSecondary,
            modifier = Modifier.width(14.dp)
        )
    }
}
