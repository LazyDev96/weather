package com.example.weather.core.service;

import com.example.weather.core.data.LocationRepository;
import com.example.weather.core.data.SettingsRepository;
import com.example.weather.core.data.WeatherRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class WeatherForegroundService_MembersInjector implements MembersInjector<WeatherForegroundService> {
  private final Provider<WeatherRepository> weatherRepositoryProvider;

  private final Provider<LocationRepository> locationRepositoryProvider;

  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public WeatherForegroundService_MembersInjector(
      Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    this.weatherRepositoryProvider = weatherRepositoryProvider;
    this.locationRepositoryProvider = locationRepositoryProvider;
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  public static MembersInjector<WeatherForegroundService> create(
      Provider<WeatherRepository> weatherRepositoryProvider,
      Provider<LocationRepository> locationRepositoryProvider,
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new WeatherForegroundService_MembersInjector(weatherRepositoryProvider, locationRepositoryProvider, settingsRepositoryProvider);
  }

  @Override
  public void injectMembers(WeatherForegroundService instance) {
    injectWeatherRepository(instance, weatherRepositoryProvider.get());
    injectLocationRepository(instance, locationRepositoryProvider.get());
    injectSettingsRepository(instance, settingsRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.example.weather.core.service.WeatherForegroundService.weatherRepository")
  public static void injectWeatherRepository(WeatherForegroundService instance,
      WeatherRepository weatherRepository) {
    instance.weatherRepository = weatherRepository;
  }

  @InjectedFieldSignature("com.example.weather.core.service.WeatherForegroundService.locationRepository")
  public static void injectLocationRepository(WeatherForegroundService instance,
      LocationRepository locationRepository) {
    instance.locationRepository = locationRepository;
  }

  @InjectedFieldSignature("com.example.weather.core.service.WeatherForegroundService.settingsRepository")
  public static void injectSettingsRepository(WeatherForegroundService instance,
      SettingsRepository settingsRepository) {
    instance.settingsRepository = settingsRepository;
  }
}
