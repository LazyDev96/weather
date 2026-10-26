package com.example.weather.core.database.di;

import com.example.weather.core.database.AppDatabase;
import com.example.weather.core.database.WeatherDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideWeatherDaoFactory implements Factory<WeatherDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideWeatherDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public WeatherDao get() {
    return provideWeatherDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideWeatherDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideWeatherDaoFactory(databaseProvider);
  }

  public static WeatherDao provideWeatherDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideWeatherDao(database));
  }
}
