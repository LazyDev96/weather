package com.example.weather.feature.explore

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.weather.core.data.model.WeatherLocation
import com.example.weather.core.ui.component.PlaceholderContent
import com.example.weather.core.ui.mvi.CollectEffects
import com.example.weather.core.ui.theme.AppTheme

@Composable
internal fun ExploreRoute(
    onCitySelected: () -> Unit,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            ExploreEffect.NavigateToHome -> onCitySelected()
        }
    }

    ExploreScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
internal fun ExploreScreen(
    state: ExploreState,
    onEvent: (ExploreEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { onEvent(ExploreEvent.QueryChanged(it)) },
            label = { Text(stringResource(R.string.explore_search_label)) },
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        )

        if (state.status == SearchStatus.LOADING) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            Spacer(modifier = Modifier.height(4.dp))
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                state.results.isNotEmpty() -> ResultsList(
                    results = state.results,
                    selectedId = state.selectedId,
                    onClick = { onEvent(ExploreEvent.ResultClicked(it)) },
                )
                state.status == SearchStatus.IDLE -> MessageContent(
                    icon = Icons.Filled.Search,
                    title = stringResource(R.string.explore_idle_title),
                    hint = stringResource(R.string.explore_idle_hint),
                )
                state.status == SearchStatus.SUCCESS -> MessageContent(
                    icon = Icons.Filled.Search,
                    title = stringResource(R.string.explore_empty_title),
                    hint = stringResource(R.string.explore_empty_hint),
                )
                state.status == SearchStatus.ERROR -> MessageContent(
                    icon = Icons.Filled.Warning,
                    title = stringResource(R.string.explore_error_title),
                    hint = stringResource(R.string.explore_error_hint),
                    actionLabel = stringResource(R.string.explore_retry),
                    onAction = { onEvent(ExploreEvent.RetryClicked) },
                )
                // LOADING без результатов: индикатор уже показан над списком
            }
        }
    }
}

@Composable
private fun ResultsList(
    results: List<WeatherLocation>,
    selectedId: String?,
    onClick: (WeatherLocation) -> Unit,
) {
    LazyColumn {
        items(items = results, key = { it.id }) { location ->
            ListItem(
                headlineContent = { Text(location.name) },
                supportingContent = location.region?.let { region -> { Text(region) } },
                trailingContent = if (location.id == selectedId) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(R.string.explore_selected),
                        )
                    }
                } else {
                    null
                },
                modifier = Modifier.clickable { onClick(location) },
            )
        }
    }
}

@Composable
private fun MessageContent(
    icon: ImageVector,
    title: String,
    hint: String,
    actionLabel: String? = null,
    onAction: () -> Unit = {},
) {
    PlaceholderContent(title = title, icon = icon) {
        Text(
            text = hint,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
        if (actionLabel != null) {
            Button(onClick = onAction) { Text(actionLabel) }
        }
    }
}

private val PreviewResults = listOf(
    WeatherLocation(id = "551487", name = "Казань", latitude = 55.79, longitude = 49.12, region = "Татарстан"),
    WeatherLocation(id = "1", name = "Казанское", latitude = 55.0, longitude = 65.0, region = "Тюменская область"),
)

@Preview(showBackground = true)
@Composable
private fun ExploreScreenResultsPreview() {
    AppTheme {
        ExploreScreen(
            state = ExploreState(
                query = "Каз",
                results = PreviewResults,
                status = SearchStatus.SUCCESS,
                selectedId = "551487",
            ),
            onEvent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExploreScreenIdlePreview() {
    AppTheme { ExploreScreen(state = ExploreState(), onEvent = {}) }
}
