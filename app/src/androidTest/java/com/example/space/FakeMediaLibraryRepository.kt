package com.example.space

import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Collection
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Data
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Link
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import retrofit2.Response

class FakeMediaLibraryRepository: MediaLibraryRepository {
    override suspend fun getData(query: String): Response<NasaLibraryResponse> {
        return Response.success(
            NasaLibraryResponse(
            collection = Collection(
                version = "1.0",
                href = "/Users/sammorton/StudioProjects/Space/app/src/androidTest/java/com/example/space/fake1.json",
                items = listOf(
                    Item(
                        href = "",
                        data = listOf(
                            Data(
                            center = "JSC",
                            date_created = "1969-07-21T00:00:00Z",
                            description = "Test Description",
                            keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
                            media_type = "image",
                            title = "Test Title",
                            nasa_id = "as11-40-5874",
                            secondary_creator = "Test Creator",
                            description_508 = "Test Description508"

                        )
                        ),
                        links = listOf(
                            Link(
                            href = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
                            rel = "preview",
                            render = "image"
                        )
                        )
                    )
                )
            )
        )
        )
    }

    override suspend fun getVideoData(url: String): Response<String> {
        val itemJsonLinkForVideo = "[\"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~orig.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123.vtt\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~medium.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~mobile.mp4\"]"
        return Response.success(itemJsonLinkForVideo)
    }
}