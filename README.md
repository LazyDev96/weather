# Weather

Мультиплатформенное приложение с погодой для городов России: Android и iOS с общим интерфейсом на Compose Multiplatform. Многомодульная архитектура, MVI, офлайн-кэш в Room, на Android ещё и постоянное уведомление с текущей погодой.

Данные о погоде и поиск городов предоставляет [Open-Meteo](https://open-meteo.com/) (ключ API не нужен).

## Возможности

- **Splash.** Экран-заставка с логотипом и плавным fade-переходом на главный экран.
- **Погода (Home).** Текущая температура, ощущаемая температура, влажность, ветер, состояние и прогноз на 5 дней. Последние данные хранятся в Room, поэтому экран открывается сразу и работает без сети. Ручное обновление всегда идёт в сеть.
- **Поиск города (Explore).** Поиск российских населённых пунктов от 3 символов с задержкой ввода. Выбранный город сохраняется и используется на Home.
- **Избранное (Favorites).** Города добавляются в избранное сердечком на Home. Нажатие на город в списке делает его выбранным и открывает Home.
- **Настройки (Settings).** Отдельные экраны для языка (русский, английский; по умолчанию английский) и темы (светлая, тёмная, системная). Язык и тема применяются сразу и сохраняются.
- **Уведомление о погоде (только Android).** Foreground service показывает в статус-баре постоянное уведомление: город, температура и состояние, а в раскрытом виде максимум и минимум за день, ощущаемая температура, влажность и ветер. Включается в настройках, на iOS переключатель скрыт.
- **Локализация.** Интерфейс на русском и английском, язык приложения переключается без смены языка системы.

## Технологии

| Область | Что используется |
| --- | --- |
| Платформы | Kotlin Multiplatform: Android и iOS (`iosArm64`, `iosSimulatorArm64`, `iosX64`) |
| Язык | Kotlin 2.2 |
| UI | Compose Multiplatform 1.9, Material 3, JetBrains Navigation Compose (type-safe маршруты), `composeResources` для строк и картинок |
| Архитектура | Многомодульность, MVI, однонаправленный поток данных |
| DI | Koin (`koin-core`, `koin-compose-viewmodel`) |
| Сеть | Ktor Client (OkHttp на Android, Darwin на iOS), kotlinx.serialization |
| База данных | Room Multiplatform, `BundledSQLiteDriver`, KSP |
| Настройки | Preferences DataStore (multiplatform) |
| Время | kotlinx-datetime 0.7 |
| Асинхронность | Kotlin Coroutines, Flow |
| Фоновая работа | Android: foreground service (`specialUse`) |
| Тесты | JUnit 4, MockK, Turbine, kotlinx-coroutines-test, Robolectric, Compose UI Test |
| Сборка | Gradle (Kotlin DSL), version catalog, convention-плагины в `build-logic`, XcodeGen для iOS-проекта |

Версии лежат в `gradle/libs.versions.toml`. Целевой SDK 35, минимальный 26, iOS 16 и новее. Kotlin, KSP и Compose Multiplatform обновляются вместе: версия KSP начинается с версии Kotlin, а Compose Multiplatform привязан к определённым версиям Kotlin.

## Сборка и запуск

Общее: JDK 17. Если нет `gradlew`, один раз выполните `gradle wrapper --gradle-version 8.10.2`.

### Android

Нужен Android SDK 35. Откройте проект в Android Studio, дождитесь Gradle Sync и запустите конфигурацию `androidApp`. Из терминала:

```
./gradlew :androidApp:assembleDebug
./gradlew testDebugUnitTest
```

### iOS

Нужны macOS, Xcode 15 или новее и [XcodeGen](https://github.com/yonaskolb/XcodeGen).

```
brew install xcodegen
cd iosApp
xcodegen generate
open iosApp.xcodeproj
```

Выберите симулятор или устройство и запустите схему `iosApp`. Перед сборкой Xcode сам вызывает `./gradlew :shared:embedAndSignAppleFrameworkForXcode` (скрипт описан в `iosApp/project.yml`), поэтому Gradle должен запускаться из терминала Xcode: нужны JDK и, при необходимости, `JAVA_HOME`. Для запуска на устройстве укажите свою команду подписи (Team) в настройках таргета.

## Структура проекта

```
androidApp               Android-оболочка: Application (запуск Koin), MainActivity, манифест, иконки, тема запуска
iosApp                   iOS-оболочка: SwiftUI-обёртка над Compose, project.yml для XcodeGen, ассеты
shared                   Общий корень: App(), корневой NavHost (Splash -> Main), initKoin, MainViewController для iOS,
                         сборка iOS-фреймворка Shared
core/
  common                 MVI-база: UiState, UiEvent, UiEffect, MviViewModel
  ui                     Тема, CollectEffects, общие компоненты, платформенные точки: язык приложения,
                         динамические цвета, поддержка уведомления
  network                Ktor: OpenMeteoApi, GeocodingApi, DTO, Koin-модуль
  database               Room: AppDatabase, DAO, миграции, Koin-модуль, путь к БД на каждой платформе
  data                   Репозитории (погода, города, избранное, настройки), доменные модели, DataStore
  service                Только Android: WeatherForegroundService и уведомление
  testing                Общие зависимости и хелперы для тестов
feature/
  splash                 Заставка
  main                   Контейнер: нижняя навигация и вложенный NavHost вкладок
  home                   Погода выбранного города
  explore                Поиск города
  favorites              Избранные города
  settings               Настройки: список, экран языка, экран темы
build-logic/             Convention-плагины: weather.kmp.library, .compose, .room (KSP), weather.android.application, .library
```

Зависимости между модулями:

```
androidApp ──> shared ──> feature:* ──> core:ui ──> core:common
   │             │             │
   │             │             └──────> core:data ──> core:network
   │             │                         │
   │             └─────────────────────────└───────> core:database
   └──> core:service ──> core:data
```

- `feature:*` зависят от `core:*`, но не друг от друга. Исключение: `feature:main` знает о вкладках, чтобы собрать их в навигацию.
- `shared` собирает модули в один граф Koin и один UI-корень, бизнес-логики в нём нет.
- `core:ui` отдаёт Compose, Koin и `core:common` через `api`, поэтому feature-модулям достаточно `implementation(projects.core.ui)`.
- Весь код, кроме платформенных точек, лежит в `commonMain`. Платформенное разделено через `expect`/`actual`: БД (путь к файлу), DataStore (путь к файлу), язык приложения, динамические цвета, признак поддержки уведомления.

## Архитектура

### MVI

```
UI --onEvent(Event)--> ViewModel --setState--> State --> UI
                                \--sendEffect--> Effect --> UI (один раз)
```

- **State** — неизменяемый `data class`, единственный источник правды для экрана.
- **Event** — всё, что UI сообщает ViewModel (нажатия, ввод).
- **Effect** — одноразовые действия: сообщение, навигация. Доставляются через `Channel` и не теряются при повороте экрана.

В каждом экране три файла: `XxxContract.kt` (State, Event, Effect), `XxxViewModel.kt` и `XxxScreen.kt`. В нём `XxxRoute` получает ViewModel из Koin (`koinViewModel()`) и связывает её с UI, а `XxxScreen` не знает о ViewModel и получает только состояние, поэтому его удобно показывать в превью и тестировать. ViewModel каждой фичи объявлены в её Koin-модуле (`homeModule`, `settingsModule` и т.д.).

### Внедрение зависимостей

Граф собирает `initKoin()` из `shared`. Android вызывает его в `WeatherApp.onCreate` и передаёт `androidContext`, iOS вызывает `startKoinIos()` из `iOSApp.init`. Платформенные части графа (билдер Room, путь к DataStore) подключаются через `includes()` из `expect`-модулей.

### Навигация

- Маршруты — `@Serializable data object` в каждом feature-модуле, граф подключается функцией вида `NavGraphBuilder.homeScreen()`.
- Корневой граф в `shared`: `Splash -> Main`, Splash удаляется из back stack.
- Вкладки живут во вложенном `NavHost` в `feature:main`, состояние каждой сохраняется при переключении.
- Вкладка Settings — вложенный граф: список настроек, экран языка и экран темы. Нижняя панель остаётся видна на подэкранах.

### Данные

Единственный источник правды для UI — Room.

```
OpenMeteoApi --> WeatherRepository.refresh --> Room (weather, weather_daily)
                                                    |
HomeViewModel <-- WeatherRepository.observe <-------+
```

- При открытии Home сразу показывается кэш, а сеть опрашивается только если данным больше 30 минут.
- Если ручное обновление не удалось, на экране остаются данные из кэша и показывается Snackbar. Если кэша нет, показывается экран ошибки с повтором.
- Поиск городов использует Open-Meteo Geocoding API (`countryCode=RU`), в результатах остаются только населённые пункты. Выбранный город хранится в Room.
- Избранное хранится в Room (`favorite_locations`).
- Язык, тема и состояние уведомления хранятся в Preferences DataStore (`SettingsRepository`).
- Один `HttpClient` на оба API. Движок выбирается по зависимостям платформы, ошибки HTTP превращаются в исключения (`expectSuccess`), а репозитории оборачивают их в `Result`.

### База данных

Room, версия 4. Таблицы: `weather`, `weather_daily`, `selected_location`, `favorite_locations`. Схемы экспортируются в `core/database/schemas`. Миграции написаны вручную в `Migrations.kt` на `SQLiteConnection`, SQL в них должен совпадать со схемой сущностей. На обеих платформах используется один и тот же `BundledSQLiteDriver`, поэтому версия SQLite не зависит от ОС. Реализацию `AppDatabaseConstructor` для каждой платформы генерирует Room.

### Язык и тема

`App()` читает настройки из `MainViewModel` и оборачивает интерфейс в `ProvideAppLocale(languageTag)` и `AppTheme(darkTheme)`. Язык применяется на уровне Compose: на Android через `Locale.setDefault` и конфигурацию ресурсов, на iOS через `AppleLanguages`. Строки лежат в `composeResources/values` (английский) и `values-ru` (русский) каждого модуля. Пока настройки не прочитаны, Android держит системный splash, чтобы не мигала чужая тема. Языка приложения в системных настройках Android 13 больше нет: язык выбирается внутри приложения.

### Уведомление о погоде (Android)

`WeatherForegroundService` из `core:service` читает погоду выбранного города из Room, раз в 30 минут и при смене города обновляет её из сети и перерисовывает уведомление. Язык уведомления берётся из настроек приложения.

- Запуск и остановку делает `MainActivity`, наблюдая за настройкой. Стартует служба только пока приложение на экране: из фона Android 12 и новее не позволяет запускать foreground service.
- Тип службы `specialUse`: у `dataSync` на Android 15 есть лимит 6 часов. При публикации в Google Play назначение службы нужно описать в Play Console.
- На Android 13 и новее запрашивается разрешение `POST_NOTIFICATIONS`. Без него служба работает, но уведомление не видно.
- iOS не разрешает постоянные уведомления, поэтому на этой платформе функция отсутствует.

## Тесты

```
./gradlew testDebugUnitTest
```

- **Unit-тесты ViewModel** в `feature/*/src/androidUnitTest`: Splash, Home, Explore, Favorites, Settings, Language, Theme. Репозитории заменены MockK, время (debounce, delay) управляется виртуальными часами `runTest`, одноразовые эффекты проверяются через Turbine.
- **Compose UI тесты** рядом (`*ScreenTest`, `MainBottomBarTest`) работают на JVM через Robolectric, эмулятор не нужен. Экранам передаётся `State`, события собираются в список и сравниваются. Тексты берутся из `composeResources/values/strings.xml` модуля через хелпер `string("имя", аргументы...)`, поэтому правка строк тесты не ломает.
- **`core:testing`** содержит общие зависимости, `MainDispatcherRule` (подмена `Dispatchers.Main`), `TestData` и хелпер `string`.

Тесты лежат в `androidUnitTest`, а не в `commonTest`, потому что MockK не мультиплатформенный. Они проверяют общий код, но только на JVM. Не покрыты: связка `Route` с Koin, реальная навигация между вкладками, репозитории и Room, весь iOS.

## Источники данных

- Погода: [Open-Meteo Forecast API](https://open-meteo.com/en/docs).
- Поиск городов: [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api), данные GeoNames.

Open-Meteo бесплатен для некоммерческого использования, его данные распространяются под лицензией CC BY 4.0 и требуют указания источника. Подробности на [open-meteo.com/en/license](https://open-meteo.com/en/license).
