package com.danielwaiguru.weatherapp.domain.utils

import javax.inject.Qualifier

@Qualifier
@Retention
annotation class Dispatcher(val dispatcherProvider: DispatcherProvider)

enum class DispatcherProvider {
    IO, MAIN
}
