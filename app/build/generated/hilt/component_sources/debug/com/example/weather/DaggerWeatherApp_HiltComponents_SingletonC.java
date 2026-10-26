package com.example.weather;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.example.weather.core.data.FavoritesRepository;
import com.example.weather.core.data.LocationRepository;
import com.example.weather.core.data.SettingsRepository;
import com.example.weather.core.data.WeatherRepository;
import com.example.weather.core.data.di.DataStoreModule_ProvidePreferencesDataStoreFactory;
import com.example.weather.core.database.AppDatabase;
import com.example.weather.core.database.FavoriteLocationDao;
import com.example.weather.core.database.SelectedLocationDao;
import com.example.weather.core.database.WeatherDao;
import com.example.weather.core.database.di.DatabaseModule_ProvideDatabaseFactory;
import com.example.weather.core.database.di.DatabaseModule_ProvideFavoriteLocationDaoFactory;
import com.example.weather.core.database.di.DatabaseModule_ProvideSelectedLocationDaoFactory;
import com.example.weather.core.database.di.DatabaseModule_ProvideWeatherDaoFactory;
import com.example.weather.core.network.GeocodingApi;
import com.example.weather.core.network.OpenMeteoApi;
import com.example.weather.core.network.di.NetworkModule_ProvideForecastRetrofitFactory;
import com.example.weather.core.network.di.NetworkModule_ProvideGeocodingApiFactory;
import com.example.weather.core.network.di.NetworkModule_ProvideGeocodingRetrofitFactory;
import com.example.weather.core.network.di.NetworkModule_ProvideJsonFactory;
import com.example.weather.core.network.di.NetworkModule_ProvideOkHttpClientFactory;
import com.example.weather.core.network.di.NetworkModule_ProvideOpenMeteoApiFactory;
import com.example.weather.core.service.WeatherForegroundService;
import com.example.weather.core.service.WeatherForegroundService_MembersInjector;
import com.example.weather.feature.explore.ExploreViewModel;
import com.example.weather.feature.explore.ExploreViewModel_HiltModules;
import com.example.weather.feature.favorites.FavoritesViewModel;
import com.example.weather.feature.favorites.FavoritesViewModel_HiltModules;
import com.example.weather.feature.home.HomeViewModel;
import com.example.weather.feature.home.HomeViewModel_HiltModules;
import com.example.weather.feature.settings.LanguageViewModel;
import com.example.weather.feature.settings.LanguageViewModel_HiltModules;
import com.example.weather.feature.settings.SettingsViewModel;
import com.example.weather.feature.settings.SettingsViewModel_HiltModules;
import com.example.weather.feature.settings.ThemeViewModel;
import com.example.weather.feature.settings.ThemeViewModel_HiltModules;
import com.example.weather.feature.splash.SplashViewModel;
import com.example.weather.feature.splash.SplashViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import kotlinx.serialization.json.Json;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

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
public final class DaggerWeatherApp_HiltComponents_SingletonC {
  private DaggerWeatherApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public WeatherApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements WeatherApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements WeatherApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements WeatherApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements WeatherApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements WeatherApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements WeatherApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements WeatherApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public WeatherApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends WeatherApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends WeatherApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends WeatherApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends WeatherApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(8).put(LazyClassKeyProvider.com_example_weather_feature_explore_ExploreViewModel, ExploreViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_feature_favorites_FavoritesViewModel, FavoritesViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_feature_home_HomeViewModel, HomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_feature_settings_LanguageViewModel, LanguageViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_MainViewModel, MainViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_feature_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_feature_splash_SplashViewModel, SplashViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_example_weather_feature_settings_ThemeViewModel, ThemeViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_example_weather_feature_favorites_FavoritesViewModel = "com.example.weather.feature.favorites.FavoritesViewModel";

      static String com_example_weather_feature_splash_SplashViewModel = "com.example.weather.feature.splash.SplashViewModel";

      static String com_example_weather_feature_settings_ThemeViewModel = "com.example.weather.feature.settings.ThemeViewModel";

      static String com_example_weather_feature_explore_ExploreViewModel = "com.example.weather.feature.explore.ExploreViewModel";

      static String com_example_weather_feature_home_HomeViewModel = "com.example.weather.feature.home.HomeViewModel";

      static String com_example_weather_MainViewModel = "com.example.weather.MainViewModel";

      static String com_example_weather_feature_settings_SettingsViewModel = "com.example.weather.feature.settings.SettingsViewModel";

      static String com_example_weather_feature_settings_LanguageViewModel = "com.example.weather.feature.settings.LanguageViewModel";

      @KeepFieldType
      FavoritesViewModel com_example_weather_feature_favorites_FavoritesViewModel2;

      @KeepFieldType
      SplashViewModel com_example_weather_feature_splash_SplashViewModel2;

      @KeepFieldType
      ThemeViewModel com_example_weather_feature_settings_ThemeViewModel2;

      @KeepFieldType
      ExploreViewModel com_example_weather_feature_explore_ExploreViewModel2;

      @KeepFieldType
      HomeViewModel com_example_weather_feature_home_HomeViewModel2;

      @KeepFieldType
      MainViewModel com_example_weather_MainViewModel2;

      @KeepFieldType
      SettingsViewModel com_example_weather_feature_settings_SettingsViewModel2;

      @KeepFieldType
      LanguageViewModel com_example_weather_feature_settings_LanguageViewModel2;
    }
  }

  private static final class ViewModelCImpl extends WeatherApp_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<ExploreViewModel> exploreViewModelProvider;

    private Provider<FavoritesViewModel> favoritesViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<LanguageViewModel> languageViewModelProvider;

    private Provider<MainViewModel> mainViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<SplashViewModel> splashViewModelProvider;

    private Provider<ThemeViewModel> themeViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;

      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.exploreViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.favoritesViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.languageViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.mainViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.splashViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.themeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(8).put(LazyClassKeyProvider.com_example_weather_feature_explore_ExploreViewModel, ((Provider) exploreViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_feature_favorites_FavoritesViewModel, ((Provider) favoritesViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_feature_home_HomeViewModel, ((Provider) homeViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_feature_settings_LanguageViewModel, ((Provider) languageViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_MainViewModel, ((Provider) mainViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_feature_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_feature_splash_SplashViewModel, ((Provider) splashViewModelProvider)).put(LazyClassKeyProvider.com_example_weather_feature_settings_ThemeViewModel, ((Provider) themeViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_example_weather_feature_explore_ExploreViewModel = "com.example.weather.feature.explore.ExploreViewModel";

      static String com_example_weather_feature_splash_SplashViewModel = "com.example.weather.feature.splash.SplashViewModel";

      static String com_example_weather_feature_home_HomeViewModel = "com.example.weather.feature.home.HomeViewModel";

      static String com_example_weather_MainViewModel = "com.example.weather.MainViewModel";

      static String com_example_weather_feature_favorites_FavoritesViewModel = "com.example.weather.feature.favorites.FavoritesViewModel";

      static String com_example_weather_feature_settings_ThemeViewModel = "com.example.weather.feature.settings.ThemeViewModel";

      static String com_example_weather_feature_settings_LanguageViewModel = "com.example.weather.feature.settings.LanguageViewModel";

      static String com_example_weather_feature_settings_SettingsViewModel = "com.example.weather.feature.settings.SettingsViewModel";

      @KeepFieldType
      ExploreViewModel com_example_weather_feature_explore_ExploreViewModel2;

      @KeepFieldType
      SplashViewModel com_example_weather_feature_splash_SplashViewModel2;

      @KeepFieldType
      HomeViewModel com_example_weather_feature_home_HomeViewModel2;

      @KeepFieldType
      MainViewModel com_example_weather_MainViewModel2;

      @KeepFieldType
      FavoritesViewModel com_example_weather_feature_favorites_FavoritesViewModel2;

      @KeepFieldType
      ThemeViewModel com_example_weather_feature_settings_ThemeViewModel2;

      @KeepFieldType
      LanguageViewModel com_example_weather_feature_settings_LanguageViewModel2;

      @KeepFieldType
      SettingsViewModel com_example_weather_feature_settings_SettingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.weather.feature.explore.ExploreViewModel 
          return (T) new ExploreViewModel(singletonCImpl.locationRepositoryProvider.get());

          case 1: // com.example.weather.feature.favorites.FavoritesViewModel 
          return (T) new FavoritesViewModel(singletonCImpl.favoritesRepositoryProvider.get(), singletonCImpl.locationRepositoryProvider.get());

          case 2: // com.example.weather.feature.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.weatherRepositoryProvider.get(), singletonCImpl.locationRepositoryProvider.get(), singletonCImpl.favoritesRepositoryProvider.get());

          case 3: // com.example.weather.feature.settings.LanguageViewModel 
          return (T) new LanguageViewModel(singletonCImpl.settingsRepositoryProvider.get());

          case 4: // com.example.weather.MainViewModel 
          return (T) new MainViewModel(singletonCImpl.settingsRepositoryProvider.get());

          case 5: // com.example.weather.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.settingsRepositoryProvider.get());

          case 6: // com.example.weather.feature.splash.SplashViewModel 
          return (T) new SplashViewModel();

          case 7: // com.example.weather.feature.settings.ThemeViewModel 
          return (T) new ThemeViewModel(singletonCImpl.settingsRepositoryProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends WeatherApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends WeatherApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    @Override
    public void injectWeatherForegroundService(WeatherForegroundService arg0) {
      injectWeatherForegroundService2(arg0);
    }

    private WeatherForegroundService injectWeatherForegroundService2(
        WeatherForegroundService instance) {
      WeatherForegroundService_MembersInjector.injectWeatherRepository(instance, singletonCImpl.weatherRepositoryProvider.get());
      WeatherForegroundService_MembersInjector.injectLocationRepository(instance, singletonCImpl.locationRepositoryProvider.get());
      WeatherForegroundService_MembersInjector.injectSettingsRepository(instance, singletonCImpl.settingsRepositoryProvider.get());
      return instance;
    }
  }

  private static final class SingletonCImpl extends WeatherApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<Json> provideJsonProvider;

    private Provider<Retrofit> provideGeocodingRetrofitProvider;

    private Provider<GeocodingApi> provideGeocodingApiProvider;

    private Provider<AppDatabase> provideDatabaseProvider;

    private Provider<LocationRepository> locationRepositoryProvider;

    private Provider<FavoritesRepository> favoritesRepositoryProvider;

    private Provider<Retrofit> provideForecastRetrofitProvider;

    private Provider<OpenMeteoApi> provideOpenMeteoApiProvider;

    private Provider<WeatherRepository> weatherRepositoryProvider;

    private Provider<DataStore<Preferences>> providePreferencesDataStoreProvider;

    private Provider<SettingsRepository> settingsRepositoryProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private SelectedLocationDao selectedLocationDao() {
      return DatabaseModule_ProvideSelectedLocationDaoFactory.provideSelectedLocationDao(provideDatabaseProvider.get());
    }

    private FavoriteLocationDao favoriteLocationDao() {
      return DatabaseModule_ProvideFavoriteLocationDaoFactory.provideFavoriteLocationDao(provideDatabaseProvider.get());
    }

    private WeatherDao weatherDao() {
      return DatabaseModule_ProvideWeatherDaoFactory.provideWeatherDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 3));
      this.provideJsonProvider = DoubleCheck.provider(new SwitchingProvider<Json>(singletonCImpl, 4));
      this.provideGeocodingRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 2));
      this.provideGeocodingApiProvider = DoubleCheck.provider(new SwitchingProvider<GeocodingApi>(singletonCImpl, 1));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 5));
      this.locationRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LocationRepository>(singletonCImpl, 0));
      this.favoritesRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<FavoritesRepository>(singletonCImpl, 6));
      this.provideForecastRetrofitProvider = DoubleCheck.provider(new SwitchingProvider<Retrofit>(singletonCImpl, 9));
      this.provideOpenMeteoApiProvider = DoubleCheck.provider(new SwitchingProvider<OpenMeteoApi>(singletonCImpl, 8));
      this.weatherRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<WeatherRepository>(singletonCImpl, 7));
      this.providePreferencesDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 11));
      this.settingsRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<SettingsRepository>(singletonCImpl, 10));
    }

    @Override
    public void injectWeatherApp(WeatherApp arg0) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.example.weather.core.data.LocationRepository 
          return (T) new LocationRepository(singletonCImpl.provideGeocodingApiProvider.get(), singletonCImpl.selectedLocationDao());

          case 1: // com.example.weather.core.network.GeocodingApi 
          return (T) NetworkModule_ProvideGeocodingApiFactory.provideGeocodingApi(singletonCImpl.provideGeocodingRetrofitProvider.get());

          case 2: // @javax.inject.Named("geocoding") retrofit2.Retrofit 
          return (T) NetworkModule_ProvideGeocodingRetrofitFactory.provideGeocodingRetrofit(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 3: // okhttp3.OkHttpClient 
          return (T) NetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 4: // kotlinx.serialization.json.Json 
          return (T) NetworkModule_ProvideJsonFactory.provideJson();

          case 5: // com.example.weather.core.database.AppDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 6: // com.example.weather.core.data.FavoritesRepository 
          return (T) new FavoritesRepository(singletonCImpl.favoriteLocationDao());

          case 7: // com.example.weather.core.data.WeatherRepository 
          return (T) new WeatherRepository(singletonCImpl.provideOpenMeteoApiProvider.get(), singletonCImpl.weatherDao());

          case 8: // com.example.weather.core.network.OpenMeteoApi 
          return (T) NetworkModule_ProvideOpenMeteoApiFactory.provideOpenMeteoApi(singletonCImpl.provideForecastRetrofitProvider.get());

          case 9: // @javax.inject.Named("forecast") retrofit2.Retrofit 
          return (T) NetworkModule_ProvideForecastRetrofitFactory.provideForecastRetrofit(singletonCImpl.provideOkHttpClientProvider.get(), singletonCImpl.provideJsonProvider.get());

          case 10: // com.example.weather.core.data.SettingsRepository 
          return (T) new SettingsRepository(singletonCImpl.providePreferencesDataStoreProvider.get());

          case 11: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvidePreferencesDataStoreFactory.providePreferencesDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
