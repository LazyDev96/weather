package com.example.weather.core.database.di;

import com.example.weather.core.database.AppDatabase;
import com.example.weather.core.database.FavoriteLocationDao;
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
public final class DatabaseModule_ProvideFavoriteLocationDaoFactory implements Factory<FavoriteLocationDao> {
  private final Provider<AppDatabase> databaseProvider;

  public DatabaseModule_ProvideFavoriteLocationDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public FavoriteLocationDao get() {
    return provideFavoriteLocationDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideFavoriteLocationDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new DatabaseModule_ProvideFavoriteLocationDaoFactory(databaseProvider);
  }

  public static FavoriteLocationDao provideFavoriteLocationDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideFavoriteLocationDao(database));
  }
}
