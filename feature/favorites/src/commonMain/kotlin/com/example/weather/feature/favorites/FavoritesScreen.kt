package com.example.weather.feature.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme
import com.example.weather.feature.favorites.resources.Res
import com.example.weather.feature.favorites.resources.favorites_empty
import com.example.weather.feature.favorites.resources.favorites_remove
import com.example.weather.feature.favorites.resources.favorites_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FavoritesRoute(
    onLocationSelected: () -> Unit,
    viewModel: FavoritesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            FavoritesEffect.LocationSelected -> onLocationSelected()
        }
    }

    FavoritesScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
internal fun FavoritesScreen(
    state: FavoritesState,
    onEvent: (FavoritesEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(Res.string.favorites_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp),
        )

        when {
            state.isLoading -> Unit
            state.items.isEmpty() -> Box(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Res.string.favorites_empty),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            else -> LazyColumn {
                items(items = state.items, key = { it.id }) { location ->
                    ListItem(
                        headlineContent = { Text(location.name) },
                        supportingContent = location.region?.let { region -> { Text(region) } },
                        leadingContent = { Icon(imageVector = Icons.Filled.Place, contentDescription = null) },
                        trailingContent = {
                            IconButton(onClick = { onEvent(FavoritesEvent.RemoveClicked(location.id)) }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(Res.string.favorites_remove),
                                )
                            }
                        },
                        modifier = Modifier.clickable { onEvent(FavoritesEvent.LocationClicked(location)) },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FavoritesScreenPreview() {
    AppTheme {
        FavoritesScreen(
            state = FavoritesState(
                items = listOf(
                    WeatherLocation.Default,
                    WeatherLocation(
                        id = "kazan",
                        name = "Kazan",
                        latitude = 55.79,
                        longitude = 49.12,
                        region = "Tatarstan",
                    ),
                ),
                isLoading = false,
            ),
            onEvent = {},
        )
    }
}
