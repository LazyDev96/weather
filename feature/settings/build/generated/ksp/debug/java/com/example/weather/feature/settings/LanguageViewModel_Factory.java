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
public final class LanguageViewModel_Factory implements Factory<LanguageViewModel> {
  private final Provider<SettingsRepository> settingsRepositoryProvider;

  public LanguageViewModel_Factory(Provider<SettingsRepository> settingsRepositoryProvider) {
    this.settingsRepositoryProvider = settingsRepositoryProvider;
  }

  @Override
  public LanguageViewModel get() {
    return newInstance(settingsRepositoryProvider.get());
  }

  public static LanguageViewModel_Factory create(
      Provider<SettingsRepository> settingsRepositoryProvider) {
    return new LanguageViewModel_Factory(settingsRepositoryProvider);
  }

  public static LanguageViewModel newInstance(SettingsRepository settingsRepository) {
    return new LanguageViewModel(settingsRepository);
  }
}
