// build.gradle.kts (в корне репозитория, НЕ в app/)
// НАЗНАЧЕНИЕ: объявить версии плагинов для всего проекта.
// apply false — значит "скачай, но не применяй здесь", применим в app/build.gradle.kts.
// ⚠️ Блока dependencies здесь БЫТЬ НЕ ДОЛЖНО. Зависимости — только в app/build.gradle.kts.

plugins {
    id("com.android.application") version "8.5.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.24" apply false
}
