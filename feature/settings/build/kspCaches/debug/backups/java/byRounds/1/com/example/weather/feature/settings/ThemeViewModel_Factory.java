package com.example.weather.feature.settings;

import com.example.weather.core.data.SettingsRepository;
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
public final class ThemeViewModel_Factory implements Factory<ThemeViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public ThemeViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public ThemeViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static ThemeViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new ThemeViewModel_Factory(settingsRepositoryProvider);
  }

  public static ThemeViewModel newInstance(SettingsRepository settingsRepository) {
    return new ThemeViewModel(settingsRepository);
  }
}
