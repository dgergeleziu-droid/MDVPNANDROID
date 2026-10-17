// ui/MdLogo.kt
// НАЗНАЧЕНИЕ: wordmark "M&D" в правом верхнем углу.
// ЗАЧЕМ ОТДЕЛЬНО: чтобы потом заменить Text на Image(painterResource) в одном месте.

package com.dgergeleziu.mdvpn.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgergeleziu.mdvpn.ui.theme.AppColors

@Composable
fun MdLogoBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp, end = 16.dp),
        horizontalArrangement = Arrangement.End  // прижимаем к правому краю
    ) {
        Text(
            text = "M&D",
            color = AppColors.logo.copy(alpha = 0.72f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.3.sp
        )
    }
}
