package com.example.space

import com.example.space.picture_of_the_day.domain.models.Apod
import com.example.space.picture_of_the_day.domain.repository.ApodRepository
import retrofit2.Response

class FakeApodRepository : ApodRepository {
    override suspend fun getData(): Response<Apod> {
        return Response.success(
            Apod(
                copyright = "Test",
                date = "Test",
                explanation = "Test",
                hdurl = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
                media_type = "image",
                service_version = "Test",
                title = "Test",
                url = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg"
            )
        )
    }
}