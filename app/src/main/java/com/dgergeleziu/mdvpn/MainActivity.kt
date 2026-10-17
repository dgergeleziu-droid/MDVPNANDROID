// app/src/main/java/com/dgergeleziu/mdvpn/MainActivity.kt
// НАЗНАЧЕНИЕ: точка входа приложения.
// ЧТО ДЕЛАЕТ: ставит Compose-контент при старте.
// ЗАЧЕМ ТАКОЙ ПРОСТОЙ: чтобы первая сборка точно прошла,
// потом добавим полноценный UI.

package com.dgergeleziu.mdvpn   // ← должен совпадать с namespace в build.gradle.kts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ComponentActivity — базовый класс для Compose-приложений.
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent — аналог SwiftUI-ного WindowGroup { ... }.
        setContent {
            MainScreen()
        }
    }
}

// @Composable — функция, которая описывает UI (аналог View.body в SwiftUI).
@Composable
fun MainScreen() {
    // Box — аналог ZStack (накладывает слои).
    Box(
        modifier = Modifier
            .fillMaxSize()                            // растянуться на весь экран
            .background(Color(0xFF0E1116))            // тёмный фон из нашей темы
            .padding(16.dp),
        contentAlignment = Alignment.Center           // контент по центру
    ) {
        Text(
            text = "M&D VPN",
            color = Color(0xFFE8ECF1),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
