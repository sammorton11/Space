# Space
Nasa Open API Mobile Application 


The NASA Space App is an Android application built using Kotlin and Jetpack Compose. It allows users to explore space related resources and information provided by NASA through various APIs.

Features

- Browse and search Mars rover images and videos
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
- Room - Database library for caching data
- Material Design Components - UI components following Material Design guidelines
- Download Manager - System service for downloading files
- MVVM Architecture

The application uses the MVVM architecture pattern, separating the user interface logic (View) from the business logic (ViewModel) and the data layer (Model). The ViewModel retrieves data from the repository and provides it to the View in a format that the View can use. The View updates itself based on changes to the ViewModel.



![NasaApp-APOD-01](https://user-images.githubusercontent.com/86651172/222377393-bc9c1ada-db97-4a30-bd7e-069b2cd269d5.png)
![NasaApp-APOD-02](https://user-images.githubusercontent.com/86651172/222377413-85b42b64-7bbd-4fcd-a4fb-7bbecea0ecb7.png)
![NasaApp-Details-Video-01](https://user-images.githubusercontent.com/86651172/222377418-a09cd3bd-6bc6-498f-a6be-71a3ab88f943.png)
![NasaApp-MediaLibrary-01](https://user-images.githubusercontent.com/86651172/222377422-c90418c1-1752-4fff-88ef-d658605bc821.png)
![NasaApp-MediaLibrary-02](https://user-images.githubusercontent.com/86651172/222377436-637f7946-a2dd-433d-a6c5-96b4edcba1f0.png)
![NasaApp-MediaLibrary-03](https://user-images.githubusercontent.com/86651172/222377442-a351d8eb-9fa2-4307-8d75-3ba188eb8ad4.png)
![NasaApp-SideNavBar](https://user-images.githubusercontent.com/86651172/222377451-ee72d1fe-c32f-4ca3-bfcc-8f7dc89e2852.png)
