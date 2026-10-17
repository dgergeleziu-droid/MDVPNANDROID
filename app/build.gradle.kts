// app/build.gradle.kts
// НАЗНАЧЕНИЕ: описать модуль приложения — SDK, зависимости, Compose.
// Это главный файл, который "делает" из кода APK.

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    // namespace — пакет для R-класса и BuildConfig.
    namespace = "com.dgergeleziu.mdvpn"

    // compileSdk — версия Android SDK, против которой компилируем.
    compileSdk = 34

    defaultConfig {
        // applicationId — уникальный id приложения на телефоне.
        // Менять потом нельзя без переустановки!
        applicationId = "com.dgergeleziu.mdvpn"

        // minSdk — минимальная версия Android. 24 = Android 7.0, покрывает ~99%.
        minSdk = 24

        // targetSdk — под какую версию "настроено" поведение. 34 = Android 14.
        targetSdk = 34

        versionCode = 1        // внутренний номер (для Play Store)
        versionName = "1.0"    // отображаемый
    }

    buildTypes {
        release {
            // В релизе включим обфускацию позже — пока выкл.
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Java 17 — совместимость с AGP 8.x.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    // Включаем Compose — иначе @Composable не скомпилируется.
    buildFeatures {
        compose = true
    }

    // Версия Compose Compiler, совместимая с Kotlin 1.9.24.
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // Базовая Kotlin-обвязка Android.
    implementation("androidx.core:core-ktx:1.13.1")

    // Lifecycle (нужен для ViewModel).
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Activity + Compose.
    implementation("androidx.activity:activity-compose:1.9.1")

    // Compose BOM — единая версия для всех Compose-зависимостей.
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Инструменты для Preview в Android Studio (в APK не попадают).
    debugImplementation("androidx.compose.ui:ui-tooling")
{
    // ... то, что уже было ...

    // ⬇️ ДОБАВЬ ЭТИ ДВЕ СТРОКИ ВНУТРЬ БЛОКА dependencies

    // Lifecycle-aware collectAsState (для Compose + StateFlow)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    // Расширенный набор иконок Material (power, shield, refresh и т.д.)
    implementation("androidx.compose.material:material-icons-extended")

    // ... то, что уже было ...
    }
}
