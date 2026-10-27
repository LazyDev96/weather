package com.example.weather.shared

import androidx.compose.ui.window.ComposeUIViewController
import kotlin.experimental.ExperimentalNativeApi
import kotlin.native.setUnhandledExceptionHook
import platform.UIKit.UIViewController

/** Вызывается из Swift (`import Shared`): `MainViewControllerKt.MainViewController()`. */
fun MainViewController(): UIViewController = ComposeUIViewController { App() }

/** Запуск Koin до показа первого экрана: `MainViewControllerKt.startKoinIos()`. */
@OptIn(ExperimentalNativeApi::class)
fun startKoinIos() {
    // Необработанное исключение в корутине на iOS завершает приложение; печатаем причину крупным маркером,
    // чтобы её было легко найти в консоли Xcode
    setUnhandledExceptionHook { error ->
        println("=== WEATHER UNCAUGHT EXCEPTION ===")
        println(error.stackTraceToString())
        println("=== END ===")
    }
    initKoin()
}
