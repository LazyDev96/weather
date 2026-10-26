package com.example.weather.feature.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.CurrentWeather
import com.example.weather.core.data.model.DailyWeather
import com.example.weather.core.data.model.Weather
import com.example.weather.core.data.model.WeatherCondition
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.ui.component.PlaceholderContent
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

/** Stateful-обёртка: связывает ViewModel с UI. */
@Composable
internal fun HomeRoute(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            HomeEffect.RefreshFailed -> Toast.makeText(
                context,
                context.getString(R.string.home_refresh_failed),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    HomeScreen(state = state, onEvent = viewModel::onEvent)
}

/** Stateless-экран: получает состояние и отправляет события. Удобно для превью и тестов. */
@Composable
internal fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val weather = state.weather
    val onRefresh = { onEvent(HomeEvent.RefreshClicked) }

    when {
        weather != null -> WeatherContent(
            weather = weather,
            isRefreshing = state.isRefreshing,
            isFavorite = state.isFavorite,
            onRefresh = onRefresh,
            onToggleFavorite = { onEvent(HomeEvent.FavoriteClicked) },
            modifier = modifier,
        )
        state.hasError && !state.isRefreshing -> ErrorContent(onRetry = onRefresh, modifier = modifier)
        else -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun WeatherContent(
    weather: Weather,
    isRefreshing: Boolean,
    isFavorite: Boolean,
    onRefresh: () -> Unit,
    onToggleFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val updatedAt = remember(weather.fetchedAt) { formatTime(weather.fetchedAt) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        if (isRefreshing) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = weather.location.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f),
                )
                Row {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(
                                if (isFavorite) R.string.home_favorite_remove else R.string.home_favorite_add,
                            ),
                        )
                    }
                    IconButton(onClick = onRefresh, enabled = !isRefreshing) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = stringResource(R.string.home_refresh),
                        )
                    }
                }
            }

            CurrentConditions(current = weather.current)

            HorizontalDivider()

            Text(
                text = stringResource(R.string.home_forecast_title),
                style = MaterialTheme.typography.titleMedium,
            )
            weather.daily.forEachIndexed { index, day ->
                DailyRow(day = day, isToday = index == 0)
            }

            Text(
                text = stringResource(R.string.home_updated_at, updatedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun CurrentConditions(current: CurrentWeather) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(text = current.condition.emoji(), style = MaterialTheme.typography.displayMedium)
            Column {
                Text(
                    text = stringResource(R.string.home_temperature, current.temperature.roundToInt()),
                    style = MaterialTheme.typography.displayLarge,
                )
                Text(
                    text = stringResource(current.condition.labelRes()),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        Text(
            text = listOf(
                stringResource(R.string.home_feels_like, current.apparentTemperature.roundToInt()),
                stringResource(R.string.home_humidity, current.humidity),
                stringResource(R.string.home_wind, current.windSpeed.roundToInt()),
            ).joinToString(separator = " · "),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun DailyRow(day: DailyWeather, isToday: Boolean) {
    val label = if (isToday) {
        stringResource(R.string.home_today)
    } else {
        day.date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        Text(text = day.condition.emoji(), style = MaterialTheme.typography.titleLarge)
        Text(
            text = stringResource(
                R.string.home_temperature_range,
                day.temperatureMax.roundToInt(),
                day.temperatureMin.roundToInt(),
            ),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ErrorContent(onRetry: () -> Unit, modifier: Modifier = Modifier) {
    PlaceholderContent(
        title = stringResource(R.string.home_error_title),
        icon = Icons.Filled.Warning,
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.home_error_hint),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        Button(onClick = onRetry) {
            Text(stringResource(R.string.home_retry))
        }
    }
}

private fun formatTime(epochMillis: Long): String =
    DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
        .format(Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()))

private val PreviewWeather = Weather(
    location = WeatherLocation.Default,
    current = CurrentWeather(
        temperature = 5.4,
        apparentTemperature = 2.1,
        humidity = 82,
        windSpeed = 14.0,
        condition = WeatherCondition.CLOUDY,
    ),
    daily = List(5) { i ->
        DailyWeather(
            date = LocalDate.of(2026, 10, 25).plusDays(i.toLong()),
            condition = WeatherCondition.entries[i],
            temperatureMax = 6.0 + i,
            temperatureMin = 1.0 - i,
        )
    },
    fetchedAt = 0L,
)

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    AppTheme { HomeScreen(state = HomeState(weather = PreviewWeather), onEvent = {}) }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenErrorPreview() {
    AppTheme { HomeScreen(state = HomeState(hasError = true), onEvent = {}) }
}
