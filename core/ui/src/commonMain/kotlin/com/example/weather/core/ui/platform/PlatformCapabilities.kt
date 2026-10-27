package com.example.weather.core.ui.platform

/** Постоянное уведомление с погодой (foreground service) есть только на Android. */
expect val isWeatherNotificationSupported: Boolean
