# TicketSwap Android Assessment
You are probably familiar with the Spotify service. It’s the biggest music streaming platform in the world and they have a nice and well documented API that you will be using in this assignment.

We ask you to implement an Android application that performs login and search with Spotify and some further data.

The user can be yourself or a newly set up account. Please ask us if you need help setting this up.

The app should consist of the following parts:

- Login Screen
- Search Screen - consisting of a search bar and a list of results
- Artist or Track page - consisting of the name, image, and 2 or more interesting fields from either an artist or a track item (note: you don’t have to support both types)

You’re free to use whatever libraries you wish to get the job done (e.g. Spotify SDK), however you should consider if it prevents us from being able assess the quality of your work.

API reference:

https://beta.developer.spotify.com/documentation/web-api/reference/search/search/
https://beta.developer.spotify.com/documentation/web-api/reference/tracks/
https://beta.developer.spotify.com/documentation/web-api/reference/artists/


## Requirements

### Networking
- The app must perform HTTP requests on at least one endpoints of the Spotify API

### Caching
- The app must perform some sort of caching of fetched API data, if a screen is reloaded we should expect to see the cached data before the refetched data

### Responsiveness
- The UI must be updated in real-time, according to the refresh rule explained above.

### Resilience
- The user should be informed if an error occurred while fetching data.
- If no network is available when a request is due, the app should park the call and perform it as soon as network is back.

### UI
- You can decide on your own how will the app look, if you need a guide, feel free to look at the TicketSwap app as an example.

### Third party libraries
- You are encouraged to used the Spotify SDK.
- We expect you to show some handling of HTTP requests. So OkHttp is acceptable but don't use a library like Retrofit which hides too much logic.

## Submission
Please provide a zip of the source code with which we can easily build locally and test out ourselves.

## General notes
- Write the solution in Kotlin.
- Pay attention to the quality of the code, and the overall design of your software.
- Some demonstration of testing would be nice.
- We recommend you to test your solution on real hardware.
- Document your API and classes where you think it is necessary.

Good luck!
