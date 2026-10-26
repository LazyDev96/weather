package com.example.weather.core.data;

import com.example.weather.core.database.SelectedLocationDao;
import com.example.weather.core.network.GeocodingApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class LocationRepository_Factory implements Factory<LocationRepository> {
  private final Provider<GeocodingApi> geocodingApiProvider;

  private final Provider<SelectedLocationDao> daoProvider;

  public LocationRepository_Factory(Provider<GeocodingApi> geocodingApiProvider,
      Provider<SelectedLocationDao> daoProvider) {
    this.geocodingApiProvider = geocodingApiProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public LocationRepository get() {
    return newInstance(geocodingApiProvider.get(), daoProvider.get());
  }

  public static LocationRepository_Factory create(Provider<GeocodingApi> geocodingApiProvider,
      Provider<SelectedLocationDao> daoProvider) {
    return new LocationRepository_Factory(geocodingApiProvider, daoProvider);
  }

  public static LocationRepository newInstance(GeocodingApi geocodingApi, SelectedLocationDao dao) {
    return new LocationRepository(geocodingApi, dao);
  }
}
