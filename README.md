# Vama Interview Coding Challenge (Android)

## Ignacio Guerendiain

### Demo video
[![](http://img.youtube.com/vi/DWYFfzHFtM8/0.jpg)](http://www.youtube.com/watch?v=DWYFfzHFtM8)

### Download latest APK
[Download](https://github.com/iguerendiain/vamaCodingChallenge/raw/main/gradlew)

### Libraries used (besides Realm and Jetpack Compose as required)
- [Navigation](https://developer.android.com/develop/ui/compose/navigation) for routing and navigation between screens handling
- [Hilt](https://dagger.dev/hilt/) for dependency injection
- [Retrofit](https://square.github.io/retrofit/) for endpoint calls and parsing (internally using GSon)
- [Flipper](https://fbflipper.com/) to help debug the application
- [Coil](https://coil-kt.github.io/coil/compose/) for lazy loading of images
- [Compose-State-Events](https://github.com/leonard-palm/compose-state-events) for one time event handling

### Some potential questions and considerations

- Chose to follow guidelines based on MVVM, SOLID and Clean Architecture. While I understand that some parts of the app may brake some of the principles mentioned before I tried to keep as tidy and scalable as possible considering the scope or the app and the allotted time I had to build it.
- Decided to use one global ViewModel for the whole app instead of one per screen due to the scope or the application. That's why it's initialized in the Navigation component so it can be passed to both screen components as the same instance. This also apply to the repository, db, and API which may be splitted into different parts or even app modules with a larger scope application. Which also made the use of the "UseCase" pattern useless.
- While the "correct" way of handling API/DB source of data would be in the repository, I choose to have that logic in the ViewModel since it's not agnostic to the app or the user.
- **Why 'id' is hardcoded to '1' in `FeedRealm`?**: While in this version of the app, the only thing I need to store regarding the feed is the copyright information, I thought that it would be more standard to store the Feed itself. In order to have only one feed in the DB I chose to fix its id to a fixed number so each time is copied to the realm it gets updated.
- For a bigger application I would implement a more generic way of handling the API errors. At the moment, that logic is directly in the same place as the place where the call is made. Same for the parsing of the error so it can be displayed in the error dialog.
- **Why an error is shown to the user only if there are no albums stored in the DB?**: The documentation clearly specifies that this error should only be shown when the DB is empty (*Allow the user to retry the network call if it fails and there isn’t any data in the local database*). Besides that, because the user cannot force the refresh of the data (*Except in debug versions where a button allows it*) there's no need to inform the user if a *"background"* update failed.
- **Why the `back` and `Open on iTunes` buttons in the Details screen does not use a ViewModel event and instead perform the navigation directly in the component?** The ViewModel should not be *"aware"* of stuff that's specific to the platform like navigation or Intents. Besides that, there are no further action required in both cases beyond navigation, no data managed by the ViewModel is changed.