An offline-first mobile app that shows current location weather and 5-day forecast written 100% in Kotlin and Jetpack Compose using Android Jetpack Components.
## Prerequisites
* Android Studio Meerkat or later
* Clone the repo
* Rename secrets.properties.example to secrets.properties and add your OpenWeatherMap API key. You can get a free API key from [OpenWeatherMap](https://openweathermap.org/api).
* To use this app, make sure you accept the location permission request to grant the app your location details.

## Tech-stack
* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a modern, cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - lightweight threads to perform asynchronous tasks.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - a stream of data that emits multiple values sequentially.
    * [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#:~:text=StateFlow%20is%20a%20state%2Dholder,property%20of%20the%20MutableStateFlow%20class.) - Flow APIs that enable flows to emit updated state and emit values to multiple consumers optimally.
    * [Dagger Hilt](https://dagger.dev/hilt/) - a dependency injection library for Android built on top of [Dagger](https://dagger.dev/) that reduces the boilerplate of doing manual injection.
    * [Moshi Converter](https://github.com/square/retrofit/blob/master/retrofit-converters/moshi/README.md) A JSON serialization converter which uses Moshi
    * [Jetpack](https://developer.android.com/jetpack)
        * [Jetpack Compose](https://developer.android.com/jetpack/compose) - A modern toolkit for building native Android UI
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform actions in response to a change in the lifecycle state.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data lifecycle in a conscious manner and survive configuration change.
        * [Room](https://developer.android.com/training/data-storage/room) - An ORM that provides an abstraction layer over SQLite to allow fluent database access.
    * [Chucker](https://github.com/ChuckerTeam/chucker) An on-device Http inspector for Android and OkHttp.
    * [Timber](https://github.com/JakeWharton/timber) - a highly extensible Android logger.
    * [Jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html) - A Gradle plugin that provides code coverage metrics for Java code.

* Architecture
    * MVVM - Model View ViewModel
* Tests
    * [JUnit](https://junit.org/junit4/) - a simple framework for writing repeatable tests.
    * [MockK](https://github.com/mockk) - mocking library for Kotlin
    * [Turbine](https://github.com/cashapp/turbine) - A testing library for Kotlin Flows
    * [Truth](https://github.com/agoda-com/Kakao) - A fluent assertions library for Android and Java.
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - An alternative syntax for writing Gradle build scripts using Koltin.
    * [Version Catalogs](https://developer.android.com/build/migrate-to-catalogs) - A scalable way of maintaining dependencies and plugins in a multi-module project.
    * [Convention Plugins](https://docs.gradle.org/current/samples/sample_convention_plugins.html) - A way to encapsulate and reuse common build configuration in Gradle, see [here](https://github.com/daniel-waiguru/WeatherApp/tree/main/build-logic%2Fconvention%2Fsrc%2Fmain%2Fjava)
    * Plugins
        * [Ktlint](https://github.com/JLLeitschuh/ktlint-gradle) - creates convenient tasks in your Gradle project that run ktlint checks or do code auto format.
        * [Spotless](https://github.com/diffplug/spotless) - format Java, groovy, markdown, and license headers using gradle.
* CI/CD
    * [GitHub Actions](https://github.com/features/actions)
 
## Dependencies

All the dependencies (external libraries) are managed using version catalogs and defined in a single place `gradle/libs.versions.toml` file. This is a scalable approach to manage dependencies and use the same dependency version across all modules.

## Code Analysis
This repo uses ktlint, a Kotlin linter, to analyze the codebase and identify potential code style violations, code quality issues, etc.
Before every commit, make sure you run the following bash script:

```shell script
./codeAnalysis.sh
```
Before using it for the first time, you must guarantee some running permissions:

```shell script
chmod +x codeAnalysis.sh
```

## Testing
The screenshots below show test results for tests done on this repo

#### Unit Tests
<img src="/docs/unit_tests.png"/>

#### UI Tests
<img src="/docs/ui_tests.png"/>

## App Architecture
A well-planned architecture is extremely important for any Android project; It makes it easier to maintain the app as the codebase grows and the team expands. This repo uses the MVVM pattern with clean architecture to have decoupled, testable, and maintainable code.
MVVM separates views (Activities, Fragments, or Composables) from the app's business logic. However, as the codebase grows, ViewModels start bloating, and separation of responsibilities becomes hard hence the need to use MVVM with clean architecture. 
#### Why Clean Architecture and Modularization?
  * Allows the app to scale easily
  * Easier onboarding of new team members
  * Easier to test code
  * Makes it easier to enforce coder ownership
This repo uses MVVM with Clean Architecture with the following modules:
#### Data
Contains repositories, data sources, and model classes. This layer hides the implementation details and data sources from the outside.
#### Domain
This module encapsulates complex business logic or simple logic that multiple ViewModels reuse. It contains all the use cases of the application and models independent of any framework-specific dependencies and represents the business logic.
#### Presentation
Contains views (in this app, Composable) and ViewModels. The views post events to the ViewModel and subscribe to the updated state.
#### Design System
Contains reusable UI components, Color, Typography, and Theme that can be reused across various modules 
#### Testing
This module contains test code, test resources, and test dependencies.

## App Screenshots
<img src="/docs/permission_screen.png" width="260"/>&emsp;<img src="/docs/loading_screen.png" width="260"/>&emsp;<img src="/docs/weather_screen.png" width="260"/>
