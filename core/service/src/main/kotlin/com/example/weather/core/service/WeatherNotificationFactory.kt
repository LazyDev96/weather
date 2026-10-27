package com.example.weather.core.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherCondition
import kotlin.math.roundToInt
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

internal const val CHANNEL_ID = "weather"

/** Собирает уведомление. [context] должен быть уже локализован под язык приложения. */
internal class WeatherNotificationFactory(private val context: Context) {

    fun createChannel() {
        val manager = context.getSystemService(NotificationManager::class.java)
        // IMPORTANCE_LOW: без звука и вибрации, уведомление не всплывает при каждом обновлении
        val channel = NotificationChannel(
            CHANNEL_ID,
            context.getString(R.string.weather_channel_name),
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = context.getString(R.string.weather_channel_description)
            setShowBadge(false)
        }
        manager.createNotificationChannel(channel)
    }

    fun placeholder(title: String?, @StringRes textRes: Int): Notification =
        base(title ?: context.getString(R.string.weather_channel_name))
            .setContentText(context.getString(textRes))
            .build()

    fun forWeather(weather: Weather): Notification {
        // «Текущий день»: запись на сегодня, а если кэш устарел и её нет — ближайшая
        val today = weather.daily.firstOrNull { it.date == Clock.System.todayIn(TimeZone.currentSystemDefault()) } ?: weather.daily.firstOrNull()
        val current = weather.current

        val summary = context.getString(
            R.string.weather_summary,
            current.temperature.roundToInt(),
            context.getString(current.condition.labelRes()),
        )
        val details = context.getString(
            R.string.weather_details,
            today?.temperatureMax?.roundToInt() ?: current.temperature.roundToInt(),
            today?.temperatureMin?.roundToInt() ?: current.temperature.roundToInt(),
            current.apparentTemperature.roundToInt(),
            current.humidity,
            current.windSpeed.roundToInt(),
        )

        return base(weather.location.name)
            .setContentText(summary)
            .setStyle(NotificationCompat.BigTextStyle().bigText("$summary\n$details"))
            .build()
    }

    private fun base(title: String): NotificationCompat.Builder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_weather)
            .setContentTitle(title)
            .setCategory(NotificationCompat.CATEGORY_STATUS)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .setSilent(true)
            .setShowWhen(false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .setContentIntent(openAppIntent())
            .addAction(0, context.getString(R.string.weather_stop), stopIntent())

    private fun openAppIntent(): PendingIntent? {
        val launch = context.packageManager.getLaunchIntentForPackage(context.packageName) ?: return null
        return PendingIntent.getActivity(
            context,
            0,
            launch,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    private fun stopIntent(): PendingIntent = PendingIntent.getService(
        context,
        1,
        Intent(context, WeatherForegroundService::class.java).setAction(WeatherForegroundService.ACTION_STOP),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
}

@StringRes
private fun WeatherCondition.labelRes(): Int = when (this) {
    WeatherCondition.CLEAR -> R.string.weather_condition_clear
    WeatherCondition.PARTLY_CLOUDY -> R.string.weather_condition_partly_cloudy
    WeatherCondition.CLOUDY -> R.string.weather_condition_cloudy
    WeatherCondition.FOG -> R.string.weather_condition_fog
    WeatherCondition.DRIZZLE -> R.string.weather_condition_drizzle
    WeatherCondition.RAIN -> R.string.weather_condition_rain
    WeatherCondition.SNOW -> R.string.weather_condition_snow
    WeatherCondition.THUNDERSTORM -> R.string.weather_condition_thunderstorm
    WeatherCondition.UNKNOWN -> R.string.weather_condition_unknown
}
