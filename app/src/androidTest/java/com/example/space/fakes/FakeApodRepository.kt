package com.example.space.fakes

import com.example.space.picture_of_the_day.domain.models.Apod
import com.example.space.picture_of_the_day.domain.repository.ApodRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeApodRepository(private val type: String? = null) : ApodRepository {
    override suspend fun getData(): Response<Apod> {
        return when (type) {
            "success" -> {
                Response.success(
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
            "error" -> {
                Response.error(
                    500,
                    "{\"message\": \"Error fetching data\"}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
            }
            "empty" -> {
                Response.success(null)
            }
            else -> {
                Response.success(null)
            }
        }
    }
}