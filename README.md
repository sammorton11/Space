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


![Screen Shot 2023-03-06 at 9 53 18 PM](https://user-images.githubusercontent.com/86651172/223316568-b2af3849-73b7-4dd9-9c2d-185e127e8c2a.png)
![Screen Shot 2023-03-06 at 9 53 11 PM](https://user-images.githubusercontent.com/86651172/223316569-270fe612-635e-4462-9ed2-2a0a7a96a496.png)
![Screen Shot 2023-03-06 at 9 52 41 PM](https://user-images.githubusercontent.com/86651172/223316574-8fcc6f71-67fc-4cd9-a8b8-68f5b1c5790e.png)


https://user-images.githubusercontent.com/86651172/223312339-bbf526dd-532e-475d-936b-c5f30add5e89.mp4
https://user-images.githubusercontent.com/86651172/223315666-492d4d8f-a72b-419d-972f-91ff8802513b.mp4
https://user-images.githubusercontent.com/86651172/223315690-68852316-9c01-4b33-a550-b2fde484e0df.mp4
https://user-images.githubusercontent.com/86651172/223315706-7de00195-84c6-4ede-b2fd-6a078990dd85.mp4



