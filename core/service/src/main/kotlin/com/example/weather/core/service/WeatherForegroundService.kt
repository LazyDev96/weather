package com.example.weather.core.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.res.Configuration
import android.os.IBinder
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import com.example.weather.core.data.LocationRepository
import com.example.weather.core.data.SettingsRepository
import com.example.weather.core.data.WeatherRepository
import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherLocation
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

private const val NOTIFICATION_ID = 1001
private const val REFRESH_INTERVAL_MS = 30 * 60 * 1000L

/**
 * Foreground service с постоянным уведомлением о погоде выбранного города.
 *
 * Данные берутся из Room (там же их читает вкладка Home), сеть дёргается раз в 30 минут
 * и при смене города. Язык уведомления следует языку из настроек приложения.
 */
class WeatherForegroundService : Service(), KoinComponent {

    // Koin уже запущен в Application.onCreate, до создания любого сервиса
    private val weatherRepository: WeatherRepository by inject()
    private val locationRepository: LocationRepository by inject()
    private val settingsRepository: SettingsRepository by inject()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private var observeJob: Job? = null

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) {
            // Кнопка в уведомлении выключает настройку, иначе приложение запустило бы службу снова
            scope.launch {
                settingsRepository.setWeatherNotificationEnabled(false)
                stopSelf()
            }
            return START_NOT_STICKY
        }

        // startForeground нужно вызвать в течение 5 секунд после startForegroundService,
        // поэтому сначала показываем заглушку, а реальные данные подставим, когда они придут
        val factory = WeatherNotificationFactory(localizedContext(settingsLanguageTag = null))
        factory.createChannel()
        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            factory.placeholder(title = null, textRes = R.string.weather_loading),
            ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
        )

        if (observeJob?.isActive != true) observeJob = observe()
        return START_STICKY
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observe(): Job = scope.launch {
        val weatherFlow = locationRepository.observeSelected()
            .distinctUntilChanged()
            .flatMapLatest { location ->
                launchPeriodicRefresh(location)
                weatherRepository.observe(location).map { weather -> location to weather }
            }

        combine(weatherFlow, settingsRepository.settings.map { it.language.tag }.distinctUntilChanged()) {
                (location, weather), languageTag ->
            val factory = WeatherNotificationFactory(localizedContext(languageTag))
            buildNotification(factory, location, weather)
        }.collect { notification ->
            getSystemService(NotificationManager::class.java).notify(NOTIFICATION_ID, notification)
        }
    }

    private var refreshJob: Job? = null

    /** Предыдущий цикл обновления отменяется при смене города. */
    private fun launchPeriodicRefresh(location: WeatherLocation) {
        refreshJob?.cancel()
        refreshJob = scope.launch {
            while (true) {
                weatherRepository.refresh(location)
                delay(REFRESH_INTERVAL_MS)
            }
        }
    }

    private fun buildNotification(
        factory: WeatherNotificationFactory,
        location: WeatherLocation,
        weather: Weather?,
    ) = if (weather != null) {
        factory.forWeather(weather)
    } else {
        factory.placeholder(title = location.name, textRes = R.string.weather_unavailable)
    }

    /**
     * Контекст сервиса не получает язык от AppCompatDelegate на Android ниже 13,
     * поэтому подставляем локаль из настроек вручную.
     */
    private fun localizedContext(settingsLanguageTag: String?): Context {
        val tag = settingsLanguageTag ?: return this
        val configuration = Configuration(resources.configuration).apply {
            setLocale(Locale.forLanguageTag(tag))
        }
        return createConfigurationContext(configuration)
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        internal const val ACTION_STOP = "com.example.weather.core.service.STOP_WEATHER"

        /** Вызывать, пока приложение на экране: из фона Android 12+ запрещает запуск. */
        fun start(context: Context) {
            ContextCompat.startForegroundService(
                context,
                Intent(context, WeatherForegroundService::class.java),
            )
        }

        fun stop(context: Context) {
            context.stopService(Intent(context, WeatherForegroundService::class.java))
        }
    }
}
