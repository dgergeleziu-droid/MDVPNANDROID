// build.gradle.kts (в корне репозитория, НЕ в app/)
// НАЗНАЧЕНИЕ: объявить версии плагинов для всего проекта.
// apply false — значит "скачай, но не применяй здесь", применим в app/build.gradle.kts.

plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}
    // Lifecycle-aware collectAsState (для Compose + StateFlow)
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")

    // Расширенный набор иконок Material (power, shield, refresh и т.д.)
    implementation("androidx.compose.material:material-icons-extended")
