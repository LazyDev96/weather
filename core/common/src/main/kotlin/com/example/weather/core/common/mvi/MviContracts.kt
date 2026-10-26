package com.example.weather.core.common.mvi

/** Неизменяемое состояние экрана. Единственный источник правды для UI. */
interface UiState

/** Намерение пользователя или системное событие, которое UI отправляет во ViewModel. */
interface UiEvent

/** Одноразовый эффект: навигация, toast, snackbar. Не хранится в состоянии. */
interface UiEffect

/** Для экранов, у которых пока нет событий. */
sealed interface NoEvent : UiEvent

/** Для экранов, у которых пока нет одноразовых эффектов. */
sealed interface NoEffect : UiEffect
