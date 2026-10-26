package com.example.weather.core.testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider

/**
 * Строка из ресурсов в тестах Robolectric (язык по умолчанию — английский).
 * Тесты ищут элементы по тем же строкам, что показывает экран, а не по копиям текста.
 */
fun string(resId: Int, vararg formatArgs: Any): String =
    ApplicationProvider.getApplicationContext<Context>().getString(resId, *formatArgs)
