package com.example.weather.core.database;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomDatabaseKt;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WeatherDao_Impl extends WeatherDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WeatherEntity> __insertionAdapterOfWeatherEntity;

  private final EntityInsertionAdapter<DailyForecastEntity> __insertionAdapterOfDailyForecastEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteDaily;

  public WeatherDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWeatherEntity = new EntityInsertionAdapter<WeatherEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather` (`locationId`,`temperature`,`apparentTemperature`,`humidity`,`weatherCode`,`windSpeed`,`fetchedAt`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WeatherEntity entity) {
        statement.bindString(1, entity.getLocationId());
        statement.bindDouble(2, entity.getTemperature());
        statement.bindDouble(3, entity.getApparentTemperature());
        statement.bindLong(4, entity.getHumidity());
        statement.bindLong(5, entity.getWeatherCode());
        statement.bindDouble(6, entity.getWindSpeed());
        statement.bindLong(7, entity.getFetchedAt());
      }
    };
    this.__insertionAdapterOfDailyForecastEntity = new EntityInsertionAdapter<DailyForecastEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `weather_daily` (`locationId`,`date`,`weatherCode`,`temperatureMax`,`temperatureMin`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final DailyForecastEntity entity) {
        statement.bindString(1, entity.getLocationId());
        statement.bindString(2, entity.getDate());
        statement.bindLong(3, entity.getWeatherCode());
        statement.bindDouble(4, entity.getTemperatureMax());
        statement.bindDouble(5, entity.getTemperatureMin());
      }
    };
    this.__preparedStmtOfDeleteDaily = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM weather_daily WHERE locationId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCurrent(final WeatherEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfWeatherEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertDaily(final List<DailyForecastEntity> entities,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfDailyForecastEntity.insert(entities);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object replace(final WeatherEntity current, final List<DailyForecastEntity> daily,
      final Continuation<? super Unit> $completion) {
    return RoomDatabaseKt.withTransaction(__db, (__cont) -> WeatherDao_Impl.super.replace(current, daily, __cont), $completion);
  }

  @Override
  public Object deleteDaily(final String locationId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteDaily.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, locationId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteDaily.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<WeatherWithDaily> observe(final String locationId) {
    final String _sql = "SELECT * FROM weather WHERE locationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, locationId);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"weather_daily",
        "weather"}, new Callable<WeatherWithDaily>() {
      @Override
      @Nullable
      public WeatherWithDaily call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfLocationId = CursorUtil.getColumnIndexOrThrow(_cursor, "locationId");
            final int _cursorIndexOfTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "temperature");
            final int _cursorIndexOfApparentTemperature = CursorUtil.getColumnIndexOrThrow(_cursor, "apparentTemperature");
            final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
            final int _cursorIndexOfWeatherCode = CursorUtil.getColumnIndexOrThrow(_cursor, "weatherCode");
            final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "windSpeed");
            final int _cursorIndexOfFetchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "fetchedAt");
            final ArrayMap<String, ArrayList<DailyForecastEntity>> _collectionDaily = new ArrayMap<String, ArrayList<DailyForecastEntity>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey;
              _tmpKey = _cursor.getString(_cursorIndexOfLocationId);
              if (!_collectionDaily.containsKey(_tmpKey)) {
                _collectionDaily.put(_tmpKey, new ArrayList<DailyForecastEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipweatherDailyAscomExampleWeatherCoreDatabaseDailyForecastEntity(_collectionDaily);
            final WeatherWithDaily _result;
            if (_cursor.moveToFirst()) {
              final WeatherEntity _tmpCurrent;
              final String _tmpLocationId;
              _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
              final double _tmpTemperature;
              _tmpTemperature = _cursor.getDouble(_cursorIndexOfTemperature);
              final double _tmpApparentTemperature;
              _tmpApparentTemperature = _cursor.getDouble(_cursorIndexOfApparentTemperature);
              final int _tmpHumidity;
              _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
              final int _tmpWeatherCode;
              _tmpWeatherCode = _cursor.getInt(_cursorIndexOfWeatherCode);
              final double _tmpWindSpeed;
              _tmpWindSpeed = _cursor.getDouble(_cursorIndexOfWindSpeed);
              final long _tmpFetchedAt;
              _tmpFetchedAt = _cursor.getLong(_cursorIndexOfFetchedAt);
              _tmpCurrent = new WeatherEntity(_tmpLocationId,_tmpTemperature,_tmpApparentTemperature,_tmpHumidity,_tmpWeatherCode,_tmpWindSpeed,_tmpFetchedAt);
              final ArrayList<DailyForecastEntity> _tmpDailyCollection;
              final String _tmpKey_1;
              _tmpKey_1 = _cursor.getString(_cursorIndexOfLocationId);
              _tmpDailyCollection = _collectionDaily.get(_tmpKey_1);
              _result = new WeatherWithDaily(_tmpCurrent,_tmpDailyCollection);
            } else {
              _result = null;
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getFetchedAt(final String locationId,
      final Continuation<? super Long> $completion) {
    final String _sql = "SELECT fetchedAt FROM weather WHERE locationId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, locationId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Long>() {
      @Override
      @Nullable
      public Long call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Long _result;
          if (_cursor.moveToFirst()) {
            if (_cursor.isNull(0)) {
              _result = null;
            } else {
              _result = _cursor.getLong(0);
            }
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }

  private void __fetchRelationshipweatherDailyAscomExampleWeatherCoreDatabaseDailyForecastEntity(
      @NonNull final ArrayMap<String, ArrayList<DailyForecastEntity>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchArrayMap(_map, true, (map) -> {
        __fetchRelationshipweatherDailyAscomExampleWeatherCoreDatabaseDailyForecastEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `locationId`,`date`,`weatherCode`,`temperatureMax`,`temperatureMin` FROM `weather_daily` WHERE `locationId` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      _stmt.bindString(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "locationId");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfLocationId = 0;
      final int _cursorIndexOfDate = 1;
      final int _cursorIndexOfWeatherCode = 2;
      final int _cursorIndexOfTemperatureMax = 3;
      final int _cursorIndexOfTemperatureMin = 4;
      while (_cursor.moveToNext()) {
        final String _tmpKey;
        _tmpKey = _cursor.getString(_itemKeyIndex);
        final ArrayList<DailyForecastEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final DailyForecastEntity _item_1;
          final String _tmpLocationId;
          _tmpLocationId = _cursor.getString(_cursorIndexOfLocationId);
          final String _tmpDate;
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
          final int _tmpWeatherCode;
          _tmpWeatherCode = _cursor.getInt(_cursorIndexOfWeatherCode);
          final double _tmpTemperatureMax;
          _tmpTemperatureMax = _cursor.getDouble(_cursorIndexOfTemperatureMax);
          final double _tmpTemperatureMin;
          _tmpTemperatureMin = _cursor.getDouble(_cursorIndexOfTemperatureMin);
          _item_1 = new DailyForecastEntity(_tmpLocationId,_tmpDate,_tmpWeatherCode,_tmpTemperatureMax,_tmpTemperatureMin);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
