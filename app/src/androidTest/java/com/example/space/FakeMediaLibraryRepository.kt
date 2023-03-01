package com.example.space

import com.example.space.domain.models.*
import com.example.space.domain.models.Collection
import com.example.space.domain.repository.MediaLibraryRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeMediaLibraryRepository: MediaLibraryRepository {
    override suspend fun getData(query: String): Response<NasaLibraryResponse> {
        return Response.success(NasaLibraryResponse(
            collection = Collection(
                version = "1.0",
                href = "/Users/sammorton/StudioProjects/Space/app/src/androidTest/java/com/example/space/fake1.json",
                items = listOf(
                    Item(
                        href = "",
                        data = listOf(Data(
                            center = "JSC",
                            date_created = "1969-07-21T00:00:00Z",
                            description = "Test Description",
                            keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
                            media_type = "image",
                            title = "Test Title",
                            nasa_id = "as11-40-5874",
                            secondary_creator = "Test Creator",
                            description_508 = "Test Description508"

                        )),
                        links = listOf(Link(
                            href = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
                            rel = "preview",
                            render = "image"
                        ))
                    )
                )
            )
        ))
    }

    fun getDataError(): Response<NasaLibraryResponse> {
        return Response.error(404,
            "Resource not found".toResponseBody("application/json".toMediaTypeOrNull())
        ) // simulate error
    }

    override suspend fun getVideoData(url: String): Response<String> {
        val itemJsonLinkForVideo = "[\"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~orig.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123.vtt\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~medium.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~mobile.mp4\"]"
        return Response.success(itemJsonLinkForVideo)
    }
}