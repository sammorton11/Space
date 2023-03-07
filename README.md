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



![Screen Shot 2023-03-05 at 9 48 08 AM](https://user-images.githubusercontent.com/86651172/222970986-df864b86-7498-4193-b148-540552d91b81.png)
![Screen Shot 2023-03-05 at 9 48 23 AM](https://user-images.githubusercontent.com/86651172/222970987-d37441fc-021a-45e1-882e-b62309e9a6d2.png)
![Screen Shot 2023-03-05 at 9 48 35 AM](https://user-images.githubusercontent.com/86651172/222970988-4fc75ae8-9618-4005-bdbf-9485f008a7c4.png)
![Screen Shot 2023-03-05 at 9 49 23 AM](https://user-images.githubusercontent.com/86651172/222970990-a7bb028e-532c-4af2-85a0-fba722ddfb0b.png)
![Screen Shot 2023-03-05 at 9 49 43 AM](https://user-images.githubusercontent.com/86651172/222970992-6cadb0c2-495b-4e6b-9cff-db4c1d0678e8.png)
![Screen Shot 2023-03-05 at 9 49 53 AM](https://user-images.githubusercontent.com/86651172/222970996-406a5a39-c566-4a6a-a6c7-c4cbdbc56dd1.png)

