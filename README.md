# Vama Interview Coding Challenge (Android)

## Ignacio Guerendiain

### Demo video

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/p-ruXoXMHDA/0.jpg)](https://www.youtube.com/watch?v=p-ruXoXMHDA)


### Libraries used (besides Realm and Jetpack Compose as required)
- [Navigation](https://developer.android.com/develop/ui/compose/navigation): Routing and navigation between screens handling
- [Hilt](https://dagger.dev/hilt/): Dependency injection
- [Retrofit](https://square.github.io/retrofit/): Endpoint calls and parsing (internally using [Gson](https://github.com/google/gson))
- [Flipper](https://fbflipper.com/): Help to debug the application
- [Coil](https://coil-kt.github.io/coil/compose/): Image lazy loading and cachè
- [Compose-State-Events](https://github.com/leonard-palm/compose-state-events): One time event handling

### Some potential questions and considerations

- Chose to follow guidelines based on MVVM, SOLID and Clean Architecture. While I understand that some parts of the app may break some of the principles mentioned before I tried to keep it as tidy and scalable as possible considering the scope of the app and the allotted time I had to build it.
- Decided to use one global ViewModel for the whole app instead of one per screen due to the scope or the application. That's why it's initialized in the Navigation component so it can be passed to both screen components as the same instance. This also applies to the repository, DB, and API which may be splitted into different parts or even app modules within a larger scope application. Which also made the use of the "UseCases" pattern useless.
- While the *"correct"* way of handling API/DB source of data would be in the repository, I cchose to have that logic in the ViewModel since it's not agnostic to the app or the user.
- **Why 'id' is hardcoded to '1' in `FeedRealm`?**: While in this version of the app, the only thing I need to store regarding the feed is the copyright information, I thought that it would be more generic to store the Feed itself. In order to have only one feed in the DB I chose to fix its id to a fixed number so each time is copied to the realm it gets updated.
- For a bigger application I would implement a more generic way of handling the API errors. At the moment, that logic is directly in the same place as the place where the call is made. Same for the parsing of the error so it can be displayed in the error dialog.
- **Why an error is shown to the user only if there are no albums stored in the DB?**: The documentation clearly specifies that this error should only be shown when the DB is empty (*Allow the user to retry the network call if it fails and there isn’t any data in the local database*). Besides that, because the user cannot force the refresh of the data (*Except in debug versions where a button allows it*) there's no need to inform the user if a *"background"* update failed.
- **Why the `back` and `Open in iTunes` buttons in the Details screen do not use a ViewModel event and instead perform the navigation directly in the component?** The ViewModel should not be *"aware"* of stuff that's specific to the platform like navigation or Intents. Besides that, there are no further action required in both cases beyond navigation, no data managed by the ViewModel is changed.
- Found that sometimes, the *"releaseDate"* property of the albums may include only the year (For example, for album *"1441072765 - My Favourite Nursery Rhymes"*, the *"releaseDate"* is "2014"). For this reason I decided to use *"String"* instead of *"Date"* for this data in the app and parse the format just before showing it to the user.