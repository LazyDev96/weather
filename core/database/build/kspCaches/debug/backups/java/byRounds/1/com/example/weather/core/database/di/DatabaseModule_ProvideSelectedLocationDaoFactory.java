package com.example.weather.core.database.di;

import com.example.weather.core.database.AppDatabase;
import com.example.weather.core.database.SelectedLocationDao;
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
public final class DatabaseModule_ProvideSelectedLocationDaoFactory implements Factory<SelectedLocationDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideSelectedLocationDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SelectedLocationDao get() {
    return provideSelectedLocationDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideSelectedLocationDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideSelectedLocationDaoFactory(databaseProvider);
  }

  public static SelectedLocationDao provideSelectedLocationDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSelectedLocationDao(database));
  }
}
