package com.example.weather.feature.favorites;

import com.example.weather.core.data.FavoritesRepository;
import com.example.weather.core.data.LocationRepository;
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
public final class FavoritesViewModel_Factory implements Factory<FavoritesViewModel> {
  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  public FavoritesViewModel_Factory(Provider<FavoritesRepository> favoritesRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
  }

  @Override
  public FavoritesViewModel get() {
    return newInstance(favoritesRepositoryProvider.get(), locationRepositoryProvider.get());
  }

  public static FavoritesViewModel_Factory create(
      Provider<FavoritesRepository> favoritesRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider) {
    return new FavoritesViewModel_Factory(favoritesRepositoryProvider, locationRepositoryProvider);
  }

  public static FavoritesViewModel newInstance(FavoritesRepository favoritesRepository,
      LocationRepository locationRepository) {
    return new FavoritesViewModel(favoritesRepository, locationRepository);
  }
}
