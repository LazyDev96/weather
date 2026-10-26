# Weather

Android-приложение с погодой для городов России. Многомодульная архитектура, MVI, Jetpack Compose, Hilt, Room, офлайн-кэш и постоянное уведомление с текущей погодой.

Данные о погоде и поиск городов предоставляет [Open-Meteo](https://open-meteo.com/) (ключ API не нужен).

## Возможности

- **Splash.** Экран-заставка с логотипом и плавным fade-переходом на главный экран.
- **Погода (Home).** Текущая температура, ощущаемая температура, влажность, ветер, состояние и прогноз на 5 дней. Последние данные хранятся в Room, поэтому экран открывается сразу и работает без сети. Ручное обновление всегда идёт в сеть.
- **Поиск города (Explore).** Поиск российских населённых пунктов от 3 символов с задержкой ввода. Выбранный город сохраняется и используется на Home.
- **Избранное (Favorites).** Города добавляются в избранное сердечком на Home. Нажатие на город в списке делает его выбранным и открывает Home.
- **Настройки (Settings).** Отдельные экраны для языка (русский, английский; по умолчанию английский) и темы (светлая, тёмная, системная). Язык и тема применяются сразу и сохраняются. Здесь же включается уведомление о погоде.
- **Уведомление о погоде.** Foreground service показывает в статус-баре постоянное уведомление: город, температура и состояние, а в раскрытом виде максимум и минимум за день, ощущаемая температура, влажность и ветер. Выключается в настройках или кнопкой в самом уведомлении.
- **Локализация.** Интерфейс на русском и английском, язык приложения переключается без смены языка системы.

## Технологии

| Область | Что используется |
| --- | --- |
| Язык | Kotlin 2.0 |
| UI | Jetpack Compose, Material 3, Compose Navigation (type-safe маршруты) |
| Архитектура | Многомодульность, MVI, однонаправленный поток данных |
| DI | Hilt |
| Асинхронность | Kotlin Coroutines, Flow |
| База данных | Room |
| Настройки | Preferences DataStore |
| Сеть | Retrofit, OkHttp, kotlinx.serialization |
| Фоновая работа | Foreground service (`specialUse`) |
| Локали | `AppCompatDelegate.setApplicationLocales`, `locales_config.xml` |
| Тесты | JUnit 4, MockK, Turbine, kotlinx-coroutines-test, Robolectric, Compose UI Test |
| Сборка | Gradle (Kotlin DSL), version catalog, KSP |

Версии лежат в `gradle/libs.versions.toml`. Целевой SDK 35, минимальный 26. AGP, Kotlin и KSP обновляются вместе: версия KSP начинается с версии Kotlin.

## Сборка и запуск

Нужны JDK 17 и Android SDK 35.

1. Откройте проект в Android Studio и дождитесь Gradle Sync.
2. Запустите конфигурацию `app` на устройстве или эмуляторе.

Для сборки из терминала в репозитории лежит `gradle-wrapper.properties` (Gradle 8.10.2). Если нет `gradlew`, один раз выполните `gradle wrapper --gradle-version 8.10.2`, затем:

```
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

## Структура проекта

```
app                      Application, MainActivity, корневой NavHost (Splash -> Main), применение языка и темы
core/
  common                 MVI-база: UiState, UiEvent, UiEffect, MviViewModel
  ui                     Тема, CollectEffects, общие composable-компоненты
  network                Retrofit: OpenMeteoApi, GeocodingApi, DTO, Hilt-модуль
  database               Room: AppDatabase, DAO, миграции, Hilt-модуль
  data                   Репозитории (погода, города, избранное, настройки) и доменные модели
  service                WeatherForegroundService и уведомление
  testing                Общие зависимости и хелперы для тестов
feature/
  splash                 Заставка
  main                   Контейнер: нижняя навигация и вложенный NavHost вкладок
  home                   Погода выбранного города
  explore                Поиск города
  favorites              Избранные города
  settings               Настройки: список, экран языка, экран темы
```

Зависимости между модулями:

```
app ──> feature:* ──> core:ui ──> core:common
          │
          └──────────> core:data ──> core:network
                          │
                          └───────> core:database

app ──> core:service ──> core:data
app ──> core:data, core:database   (нужны для Hilt-графа)
```

- `feature:*` зависят от `core:*`, но не друг от друга. Исключение: `feature:main` знает о вкладках, чтобы собрать их в навигацию.
- `app` собирает модули вместе и не содержит бизнес-логики.
- `core:ui` отдаёт Compose и `core:common` через `api`, поэтому feature-модулям достаточно `implementation(projects.core.ui)`.

## Архитектура

### MVI

```
UI --onEvent(Event)--> ViewModel --setState--> State --> UI
                                \--sendEffect--> Effect --> UI (один раз)
```

- **State** — неизменяемый `data class`, единственный источник правды для экрана.
- **Event** — всё, что UI сообщает ViewModel (нажатия, ввод).
- **Effect** — одноразовые действия: сообщение, навигация. Доставляются через `Channel` и не теряются при повороте экрана.

В каждом экране три файла: `XxxContract.kt` (State, Event, Effect), `XxxViewModel.kt` и `XxxScreen.kt`. В нём `XxxRoute` связывает ViewModel с UI, а `XxxScreen` не знает о ViewModel и получает только состояние, поэтому его удобно показывать в превью и тестировать.

### Навигация

- Маршруты — `@Serializable data object` в каждом feature-модуле, граф подключается функцией вида `NavGraphBuilder.homeScreen()`.
- Корневой граф в `app`: `Splash -> Main`, Splash удаляется из back stack.
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
- Если ручное обновление не удалось, на экране остаются данные из кэша и показывается сообщение. Если кэша нет, показывается экран ошибки с повтором.
- Поиск городов использует Open-Meteo Geocoding API (`countryCode=RU`), в результатах остаются только населённые пункты. Выбранный город хранится в Room.
- Избранное хранится в Room (`favorite_locations`).
- Язык, тема и состояние уведомления хранятся в Preferences DataStore (`SettingsRepository`).

### База данных

Room, версия 4. Таблицы: `weather`, `weather_daily`, `selected_location`, `favorite_locations`. Схемы экспортируются в `core/database/schemas`. Миграции написаны вручную в `Migrations.kt`, SQL в них должен совпадать со схемой сущностей.

### Язык и тема

`MainActivity` наследует `AppCompatActivity` и подписывается на настройки. Язык применяется через `AppCompatDelegate.setApplicationLocales` (на Android 13 и новее он виден и в системных настройках приложения), тема передаётся в `AppTheme(darkTheme)`. Системный splash остаётся на экране, пока настройки не прочитаны, чтобы не мигала чужая тема. Строки лежат в `values` (английский) и `values-ru` (русский) каждого модуля.

### Уведомление о погоде

`WeatherForegroundService` из `core:service` читает погоду выбранного города из Room, раз в 30 минут и при смене города обновляет её из сети и перерисовывает уведомление. Язык уведомления берётся из настроек приложения.

- Запуск и остановку делает `MainActivity`, наблюдая за настройкой. Стартует служба только пока приложение на экране: из фона Android 12 и новее не позволяет запускать foreground service.
- Тип службы `specialUse`: у `dataSync` на Android 15 есть лимит 6 часов. При публикации в Google Play назначение службы нужно описать в Play Console.
- На Android 13 и новее запрашивается разрешение `POST_NOTIFICATIONS`. Без него служба работает, но уведомление не видно.

## Тесты

```
./gradlew testDebugUnitTest
```

- **Unit-тесты ViewModel** в `feature/*/src/test`: Splash, Home, Explore, Favorites, Settings, Language, Theme. Репозитории заменены MockK, время (debounce, delay) управляется виртуальными часами `runTest`, одноразовые эффекты проверяются через Turbine.
- **Compose UI тесты** рядом (`*ScreenTest`, `MainBottomBarTest`) работают на JVM через Robolectric, эмулятор не нужен. Экранам передаётся `State`, события собираются в список и сравниваются. Тексты берутся из ресурсов, поэтому правка строк тесты не ломает. При первом запуске Robolectric скачивает android-all для SDK 34.
- **`core:testing`** содержит общие зависимости, `MainDispatcherRule` (подмена `Dispatchers.Main`) и `TestData`.

Не покрыты: связка `Route` с Hilt, реальная навигация между вкладками, репозитории и Room.

## Источники данных

- Погода: [Open-Meteo Forecast API](https://open-meteo.com/en/docs).
- Поиск городов: [Open-Meteo Geocoding API](https://open-meteo.com/en/docs/geocoding-api), данные GeoNames.

Open-Meteo бесплатен для некоммерческого использования, его данные распространяются под лицензией CC BY 4.0 и требуют указания источника. Подробности на [open-meteo.com/en/license](https://open-meteo.com/en/license).
