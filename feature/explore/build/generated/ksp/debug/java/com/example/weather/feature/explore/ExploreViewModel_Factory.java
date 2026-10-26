package com.example.weather.feature.explore;

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
public final class ExploreViewModel_Factory implements Factory<ExploreViewModel> {
  private final Provider<LocationRepository> repositoryProvider;

  public ExploreViewModel_Factory(Provider<LocationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ExploreViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ExploreViewModel_Factory create(Provider<LocationRepository> repositoryProvider) {
    return new ExploreViewModel_Factory(repositoryProvider);
  }

  public static ExploreViewModel newInstance(LocationRepository repository) {
    return new ExploreViewModel(repository);
  }
}
