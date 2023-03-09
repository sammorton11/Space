package com.example.space.fakes

import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Collection
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Data
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Item
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.Link
import com.example.space.nasa_media_library.domain.models.nasa_media_library_models.NasaLibraryResponse
import com.example.space.nasa_media_library.domain.repository.MediaLibraryRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeMediaLibraryRepository: MediaLibraryRepository {

    override suspend fun getData(query: String): Response<NasaLibraryResponse> {
        return when (query) {
            "success" -> {
                Response.success(fakeLibraryResponse)
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

    override suspend fun getVideoData(url: String): Response<String> {
        return when (url) {
            "success" -> {
                Response.success(itemJsonLinkForVideo)
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
    companion object {

        val fakeDataObject01 = Data(
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
        val fakeDataObject02 = Data(
            center = "JSC",
            date_created = "1969-07-21T00:00:00Z",
            description = "Test Description",
            keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
            media_type = "video",
            title = "Test Title",
            nasa_id = "as11-40-5874",
            secondary_creator = "Test Creator",
            description_508 = "Test Description508"

        )
        val fakeDataObject03 = Data(
            center = "JSC",
            date_created = "1969-07-21T00:00:00Z",
            description = "Test Description",
            keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
            media_type = "audio",
            title = "Test Title",
            nasa_id = "as11-40-5874",
            secondary_creator = "Test Creator",
            description_508 = "Test Description508"

        )
        private val fakeLinkObject01 = Link(
            href = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
            rel = "preview",
            render = "image"
        )
        // todo: needs an mp4 file
        private val fakeLinkObject02 = Link(
            href = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
            rel = "preview",
            render = "video"
        )
        // todo: needs an wav or mp3 file
        private val fakeLinkObject03 = Link(
            href = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
            rel = "preview",
            render = "audio"
        )

        const val itemJsonLinkForVideo = "[\"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~orig.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123.vtt\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~medium.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~mobile.mp4\"]"

        val fakeItemObject01 = Item(
            href = "https://images-assets.nasa.gov/image/NHQ201906010004/collection.json",
            data = listOf(fakeDataObject01),
            links = listOf(fakeLinkObject01)
        )
        val fakeItemObject02 = Item(
            href = "https://images-assets.nasa.gov/video/Space-Exploration-Video-1/collection.json",
            data = listOf(fakeDataObject02),
            links = listOf(fakeLinkObject02)
        )
        val fakeItemObject03 = Item(
            href = "https://images-assets.nasa.gov/audio/367-AAA/collection.json",
            data = listOf(fakeDataObject03),
            links = listOf(fakeLinkObject03)
        )

        val fakeLibraryResponse = NasaLibraryResponse(
            collection = Collection(
                version = "1.0",
                href = "https://images-assets.nasa.gov/image/NHQ201906010004/collection.json",
                items = listOf(fakeItemObject01, fakeItemObject02, fakeItemObject03)
            )
        )
    }
}