package com.example.weather.core.network.di;

import com.example.weather.core.network.GeocodingApi;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("javax.inject.Named")
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
public final class NetworkModule_ProvideGeocodingApiFactory implements Factory<GeocodingApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideGeocodingApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public GeocodingApi get() {
    return provideGeocodingApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideGeocodingApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideGeocodingApiFactory(retrofitProvider);
  }

  public static GeocodingApi provideGeocodingApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideGeocodingApi(retrofit));
  }
}
