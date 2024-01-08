package com.danielwaiguru.weatherapp.testing.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.danielwaiguru.weatherapp.testing.utils.MainCoroutineRule
import org.junit.Rule

abstract class BaseViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutor: InstantTaskExecutorRule = InstantTaskExecutorRule()
}