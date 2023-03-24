package com.samm.space.fakes

import com.samm.space.core.Resource
import com.samm.space.picture_of_the_day.domain.models.Apod
import com.samm.space.picture_of_the_day.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

// Todo: This doesn't really work. Passing different strings in tests doesn't change results
class FakeApodRepository(private val type: String? = null) : ApodRepository {

    override suspend fun getData(): Apod? {
        return when (type) {
            "success" -> {
                Apod(
                    copyright = "Test",
                    date = "2022-11-2023",
                    explanation = "Test",
                    hdurl = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
                    media_type = "image",
                    service_version = "Test",
                    title = "Test",
                    url = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg"
                )
            }
            "error" -> {
                 return null
            }
            "empty" -> {
                Apod(
                    copyright = "",
                    date = "2022-11-2023",
                    explanation = "",
                    hdurl = "",
                    media_type = "",
                    service_version = "",
                    title = "",
                    url = ""
                )
            }
            else -> {
                Apod(
                    copyright = "Test",
                    date = "2022-11-2023",
                    explanation = "Test",
                    hdurl = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
                    media_type = "image",
                    service_version = "Test",
                    title = "Test",
                    url = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg"
                )
            }
        }
    }

    override fun getApodData(): Flow<Resource<Apod?>> = flow {
        try {
            emit(Resource.Loading())
            val response = getData()
            emit(Resource.Success(response))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error("Check Internet Connection"))
        }
    }
}