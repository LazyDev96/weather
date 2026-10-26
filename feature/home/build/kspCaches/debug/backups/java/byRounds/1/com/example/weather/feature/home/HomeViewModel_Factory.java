package com.example.weather.feature.home;

import com.example.weather.core.data.FavoritesRepository;
import com.example.weather.core.data.LocationRepository;
import com.example.weather.core.data.WeatherRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<WeatherRepository> weatherRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  public HomeViewModel_Factory(Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    this.weatherRepositoryProvider = weatherRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(weatherRepositoryProvider.get(), locationRepositoryProvider.get(), favoritesRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new HomeViewModel_Factory(weatherRepositoryProvider, locationRepositoryProvider, favoritesRepositoryProvider);
  }

  public static HomeViewModel newInstance(WeatherRepository weatherRepository,
      LocationRepository locationRepository, FavoritesRepository favoritesRepository) {
    return new HomeViewModel(weatherRepository, locationRepository, favoritesRepository);
  }
}
