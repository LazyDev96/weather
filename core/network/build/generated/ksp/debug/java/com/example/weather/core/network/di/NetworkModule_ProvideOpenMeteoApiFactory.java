package com.example.weather.core.network.di;

import com.example.weather.core.network.OpenMeteoApi;
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
public final class NetworkModule_ProvideOpenMeteoApiFactory implements Factory<OpenMeteoApi> {
  private final Provider<Retrofit> retrofitProvider;

  public NetworkModule_ProvideOpenMeteoApiFactory(Provider<Retrofit> retrofitProvider) {
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public OpenMeteoApi get() {
    return provideOpenMeteoApi(retrofitProvider.get());
  }

  public static NetworkModule_ProvideOpenMeteoApiFactory create(
      Provider<Retrofit> retrofitProvider) {
    return new NetworkModule_ProvideOpenMeteoApiFactory(retrofitProvider);
  }

  public static OpenMeteoApi provideOpenMeteoApi(Retrofit retrofit) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOpenMeteoApi(retrofit));
  }
}
