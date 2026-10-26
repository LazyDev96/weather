package com.example.weather.core.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile FavoriteLocationDao _favoriteLocationDao;

  private volatile WeatherDao _weatherDao;

  private volatile SelectedLocationDao _selectedLocationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorite_locations` (`locationId` TEXT NOT NULL, `name` TEXT NOT NULL, `region` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`locationId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `weather` (`locationId` TEXT NOT NULL, `temperature` REAL NOT NULL, `apparentTemperature` REAL NOT NULL, `humidity` INTEGER NOT NULL, `weatherCode` INTEGER NOT NULL, `windSpeed` REAL NOT NULL, `fetchedAt` INTEGER NOT NULL, PRIMARY KEY(`locationId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `weather_daily` (`locationId` TEXT NOT NULL, `date` TEXT NOT NULL, `weatherCode` INTEGER NOT NULL, `temperatureMax` REAL NOT NULL, `temperatureMin` REAL NOT NULL, PRIMARY KEY(`locationId`, `date`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `selected_location` (`id` INTEGER NOT NULL, `locationId` TEXT NOT NULL, `name` TEXT NOT NULL, `region` TEXT, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, PRIMARY KEY(`id`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '6de9d0327249789b1fa69a646d4e9ce3')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `favorite_locations`");
        db.execSQL("DROP TABLE IF EXISTS `weather`");
        db.execSQL("DROP TABLE IF EXISTS `weather_daily`");
        db.execSQL("DROP TABLE IF EXISTS `selected_location`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsFavoriteLocations = new HashMap<String, TableInfo.Column>(6);
        _columnsFavoriteLocations.put("locationId", new TableInfo.Column("locationId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteLocations.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteLocations.put("region", new TableInfo.Column("region", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteLocations.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteLocations.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavoriteLocations.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavoriteLocations = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavoriteLocations = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavoriteLocations = new TableInfo("favorite_locations", _columnsFavoriteLocations, _foreignKeysFavoriteLocations, _indicesFavoriteLocations);
        final TableInfo _existingFavoriteLocations = TableInfo.read(db, "favorite_locations");
        if (!_infoFavoriteLocations.equals(_existingFavoriteLocations)) {
          return new RoomOpenHelper.ValidationResult(false, "favorite_locations(com.example.weather.core.database.FavoriteLocationEntity).\n"
                  + " Expected:\n" + _infoFavoriteLocations + "\n"
                  + " Found:\n" + _existingFavoriteLocations);
        }
        final HashMap<String, TableInfo.Column> _columnsWeather = new HashMap<String, TableInfo.Column>(7);
        _columnsWeather.put("locationId", new TableInfo.Column("locationId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("temperature", new TableInfo.Column("temperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("apparentTemperature", new TableInfo.Column("apparentTemperature", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("humidity", new TableInfo.Column("humidity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("weatherCode", new TableInfo.Column("weatherCode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("windSpeed", new TableInfo.Column("windSpeed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeather.put("fetchedAt", new TableInfo.Column("fetchedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeather = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeather = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWeather = new TableInfo("weather", _columnsWeather, _foreignKeysWeather, _indicesWeather);
        final TableInfo _existingWeather = TableInfo.read(db, "weather");
        if (!_infoWeather.equals(_existingWeather)) {
          return new RoomOpenHelper.ValidationResult(false, "weather(com.example.weather.core.database.WeatherEntity).\n"
                  + " Expected:\n" + _infoWeather + "\n"
                  + " Found:\n" + _existingWeather);
        }
        final HashMap<String, TableInfo.Column> _columnsWeatherDaily = new HashMap<String, TableInfo.Column>(5);
        _columnsWeatherDaily.put("locationId", new TableInfo.Column("locationId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherDaily.put("date", new TableInfo.Column("date", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherDaily.put("weatherCode", new TableInfo.Column("weatherCode", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherDaily.put("temperatureMax", new TableInfo.Column("temperatureMax", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWeatherDaily.put("temperatureMin", new TableInfo.Column("temperatureMin", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWeatherDaily = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWeatherDaily = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWeatherDaily = new TableInfo("weather_daily", _columnsWeatherDaily, _foreignKeysWeatherDaily, _indicesWeatherDaily);
        final TableInfo _existingWeatherDaily = TableInfo.read(db, "weather_daily");
        if (!_infoWeatherDaily.equals(_existingWeatherDaily)) {
          return new RoomOpenHelper.ValidationResult(false, "weather_daily(com.example.weather.core.database.DailyForecastEntity).\n"
                  + " Expected:\n" + _infoWeatherDaily + "\n"
                  + " Found:\n" + _existingWeatherDaily);
        }
        final HashMap<String, TableInfo.Column> _columnsSelectedLocation = new HashMap<String, TableInfo.Column>(6);
        _columnsSelectedLocation.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSelectedLocation.put("locationId", new TableInfo.Column("locationId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSelectedLocation.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSelectedLocation.put("region", new TableInfo.Column("region", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSelectedLocation.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSelectedLocation.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSelectedLocation = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSelectedLocation = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSelectedLocation = new TableInfo("selected_location", _columnsSelectedLocation, _foreignKeysSelectedLocation, _indicesSelectedLocation);
        final TableInfo _existingSelectedLocation = TableInfo.read(db, "selected_location");
        if (!_infoSelectedLocation.equals(_existingSelectedLocation)) {
          return new RoomOpenHelper.ValidationResult(false, "selected_location(com.example.weather.core.database.SelectedLocationEntity).\n"
                  + " Expected:\n" + _infoSelectedLocation + "\n"
                  + " Found:\n" + _existingSelectedLocation);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "6de9d0327249789b1fa69a646d4e9ce3", "ed7cf9a65c4a5cf2ac41e2d7d8e1fb97");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "favorite_locations","weather","weather_daily","selected_location");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `favorite_locations`");
      _db.execSQL("DELETE FROM `weather`");
      _db.execSQL("DELETE FROM `weather_daily`");
      _db.execSQL("DELETE FROM `selected_location`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(FavoriteLocationDao.class, FavoriteLocationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(WeatherDao.class, WeatherDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SelectedLocationDao.class, SelectedLocationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public FavoriteLocationDao favoriteLocationDao() {
    if (_favoriteLocationDao != null) {
      return _favoriteLocationDao;
    } else {
      synchronized(this) {
        if(_favoriteLocationDao == null) {
          _favoriteLocationDao = new FavoriteLocationDao_Impl(this);
        }
        return _favoriteLocationDao;
      }
    }
  }

  @Override
  public WeatherDao weatherDao() {
    if (_weatherDao != null) {
      return _weatherDao;
    } else {
      synchronized(this) {
        if(_weatherDao == null) {
          _weatherDao = new WeatherDao_Impl(this);
        }
        return _weatherDao;
      }
    }
  }

  @Override
  public SelectedLocationDao selectedLocationDao() {
    if (_selectedLocationDao != null) {
      return _selectedLocationDao;
    } else {
      synchronized(this) {
        if(_selectedLocationDao == null) {
          _selectedLocationDao = new SelectedLocationDao_Impl(this);
        }
        return _selectedLocationDao;
      }
    }
  }
}
