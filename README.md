# DisneyCodeChallenge

This App lists Maverl comics and its detail.

# Development Environment
The app is written entirely in Kotlin and uses the Gradle build system.

To build the app, use the gradlew build command or use "Import Project" in Android Studio. Android Studio Arctic Fox or newer is required.

# Running the app

Create an Account or Sig-In [here](https://developer.marvel.com/account) to get your public and private keys.
Add your public and private API keys in the file locale.properties and compile the project.

<img width="806" alt="Screen Shot 2021-11-09 at 5 23 06 PM" src="https://user-images.githubusercontent.com/11575883/141038223-292228a2-0681-4731-9c9f-6f60cd7c7a86.png">

# Architecture
The architecture is built around Android Architecture Components and follows the recommendations laid out in the Guide to App Architecture. Logic is kept away from Activities and Fragments and moved to ViewModels. Data is observed using Kotlin Flows and the Binding Library binds UI components in layouts.

The Repository layer handles data operations. The data comes from [Marvel API](https://developer.marvel.com/docs) and the repository modules are responsible for handling all data operations and abstracting the data sources from the rest of the app.

The Navigation component is used to implement navigation in the app, handling Fragment transactions and providing a consistent user experience.

UI tests are written with Espresso and unit tests use Junit4 with Mockito when necessary.

#Kotlin
The app is entirely written in Kotlin and uses Jetpack's Android Ktx extensions.

Asynchronous tasks are handled with coroutines. Coroutines allow for simple and safe management of one-shot operations as well as building and consuming streams of data using Kotlin Flows.
