# Space
Nasa Open API Mobile Application 


The NASA Space App is an Android application built using Kotlin and Jetpack Compose. It allows users to explore space related resources and information provided by NASA through various APIs.

Features

- Browse and search images and videos from NASA
- View the latest Mars weather information
- View the Astronomy Picture of the Day (APOD)
- Download and share space related files
- Utilize a staggered adaptive grid layout to view content
- Access a side navigation bar and action menu
- Implement the Model-View-ViewModel (MVVM) architecture
- Use Material Design guidelines for a polished user interface


APIs Used

- Mars Rover Photos API
- Mars Weather Service API
- Astronomy Picture of the Day API


Libraries Used

- Retrofit - HTTP client for API calls
- Coin - Image loading library
- Flow - Asynchronous data stream library
- ExoPlayer - Video player
- Room - Database library for caching data
- Material Design Components - UI components following Material Design guidelines
- Download Manager - System service for downloading files


MVVM Architecture

The application uses the MVVM architecture pattern, separating the user interface logic (View) from the business logic (ViewModel) and the data layer (Model). The ViewModel retrieves data from the repository and provides it to the View in a format that the View can use. The View updates itself based on changes to the ViewModel.


https://user-images.githubusercontent.com/86651172/223312339-bbf526dd-532e-475d-936b-c5f30add5e89.mp4


