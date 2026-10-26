package com.example.weather.core.data;

import com.example.weather.core.database.WeatherDao;
import com.example.weather.core.network.OpenMeteoApi;
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
public final class WeatherRepository_Factory implements Factory<WeatherRepository> {
  private final Provider<OpenMeteoApi> apiProvider;

  private final Provider<WeatherDao> daoProvider;

  public WeatherRepository_Factory(Provider<OpenMeteoApi> apiProvider,
      Provider<WeatherDao> daoProvider) {
    this.apiProvider = apiProvider;
    this.daoProvider = daoProvider;
  }

  @Override
  public WeatherRepository get() {
    return newInstance(apiProvider.get(), daoProvider.get());
  }

  public static WeatherRepository_Factory create(Provider<OpenMeteoApi> apiProvider,
      Provider<WeatherDao> daoProvider) {
    return new WeatherRepository_Factory(apiProvider, daoProvider);
  }

  public static WeatherRepository newInstance(OpenMeteoApi api, WeatherDao dao) {
    return new WeatherRepository(api, dao);
  }
}
