// settings.gradle.kts
// НАЗНАЧЕНИЕ: говорит Gradle, какие модули входят в проект и где брать зависимости.
// Без этого файла Gradle не поймёт структуру.

pluginManagement {
    repositories {
        google()            // плагины Android Gradle
        mavenCentral()      // Kotlin и прочее
        gradlePluginPortal() // сторонние плагины
    }
}

dependencyResolutionManagement {
    // Запрещаем модулям объявлять свои репозитории — только здесь.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MDVPN"
include(":app")   // подключаем модуль app
