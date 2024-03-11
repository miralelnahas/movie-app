# Movie App

The Movie App is a simple Android application that displays a list of movies, allows sorting based on popularity or rating, and provides detailed information about each movie.

## Installation

You can download latest APK file from the [releases](https://github.com/miralelnahas/movie-app/releases) page

Minimum Android version: Android 8.0 (Oreo).

![Screenshot_20240311_051721_com trianglz movies](https://github.com/miralelnahas/movie-app/assets/26336155/54c0ae87-a44e-41c4-9ecd-ac0b94096a30)


## Development

If we want to run the app from android studio please follow the following steps:

2. Obtain your token from the [Settings](https://www.themoviedb.org/settings/api) page of The Movie Database (TMDB)
3.  Add the token to the local.properties file as follows:  TOKEN = XXX
 
## Application Architecture

In Movies App, I followed clean architecture using Model-View-Intent (MVI) pattern

*Clean Architecture Layers:*
- **UI Layer:** Interacts with the user interface.
- **Domain Layer:** Defines user-triggered actions.
- **Core Layer:** Contains the app's business logic.
- **Data Layer:** Abstract definition of all the data sources.

*To Acheive this, I divided my app into four modules:*
- **App Module:** Manages app-level dependencies.
- **UI Module:** Handles user interface-related code.
- **Domain Module:** Implements usecases
- **Core Module:** Manages repositories
- **Data Module:** Implements the data source interfaces defined in the Core Layer

*MVI (Model-view-Intent) is a design pattern that follows a unidirectional Data Flow, The main components are:*
- **Model:** The view model that holds the state of the view at any given moment.
- **View:** The user interface presented to the user.
- **Intent:**  The events sent by the view to the model to perform actions that change the state.
  
The *DataState.kt* file in *BaseViewModel.kt* defines different states: Empty, Loading, Success (with data), and Error.
When the view model changes the state, the view updates accordingly.

## Important Technologies Used:
- **Hilt:** Dependency injection framework.
- **Main Navigation:** Handles navigation between different screens.
- **Data binding:** Binds data to views.
- **Paging:** "with RemoteMediator" Handles pagination and caching for the Movies List.
- **Room Database:** Used for local caching.
- **Picasso:** Image loading library for retrieving images from a server.
- **Shimmer:** Skeleton loading library for displaying loading placeholders.

**Note: The entire app follows the principles of Material Design.**

## Challenges Faced During Development:
1 - The IMDb database returns most popular movies which are not sorted by **popularity** and top rated movies which are not sorted by **rating** 

 **Solution:** To address this issue, an additional column (id) was added to fix the sorting problem.
 
2 - The IMDb database returns duplicate movies with duplicate IDs, making it impossible to rely on these IDs in the local database or the paging data adapter.

 **Solution:** To handle this issue, another primary key (id) was added to ensure uniqueness.
 
3 - *SearchView* is a view inside *SearchBar* and not a fragment in the navigation graph, so it was hard to let the *MainActivity* handle device's back clicks using the navigation graph

**Solution:** To overcome this challenge, a back click callback was added to *MoviesFragment* that carries *SearchView* which is added or removed according to *SearchView* visibility

