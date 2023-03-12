package com.samm.space.fakes

import com.samm.space.core.Resource
import com.samm.space.nasa_media_library.domain.models.*
import com.samm.space.nasa_media_library.domain.models.Collection
import com.samm.space.nasa_media_library.domain.repository.MediaLibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    // todo: errors not going through - this is an experiment IP
    override fun searchImageVideoLibrary(query: String) = flow {
        emit(Resource.Loading())
        val response = getData(query)
        if (!response.errorBody()?.string().isNullOrEmpty()) {
            emit(Resource.Error(response.errorBody()?.string()))
        } else {
            emit(Resource.Success(response))
        }
    }

    override fun savedQueryFlow(): Flow<String?> = flow {
        emit("Fake saved query")
    }

    override fun videoDataFlow(url: String) = flow {
        emit(Resource.Loading())
        val response = getVideoData(url)
        emit(Resource.Success(response))
    }.catch { throwable ->
        emit(Resource.Error(throwable.toString()))
    }

    companion object {
        const val itemJsonLinkForVideo = "[http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~orig.mp4, \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123.vtt\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~medium.mp4\", \"http://images-assets.nasa.gov/video/GRC-2022-CM-0123/GRC-2022-CM-0123~mobile.mp4\"]"

        val fakeDataObject01 =
            Data(
                center = "JSC",
                date_created = "1969-07-21T00:00:00Z",
                description = "Test Description1",
                keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
                media_type = "image",
                title = "Test Title",
                nasa_id = "as11-40-5874",
                secondary_creator = "Test Creator",
                description_508 = "Test Description508"

            )
        val fakeDataObject02 =
            Data(
                center = "JSC",
                date_created = "1969-07-21T00:00:00Z",
                description = "Test Description2",
                keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
                media_type = "video",
                title = "Test Title",
                nasa_id = "as11-40-5874",
                secondary_creator = "Test Creator",
                description_508 = "Test Description508"

            )
        val fakeDataObject03 =
            Data(
                center = "JSC",
                date_created = "1969-07-21T00:00:00Z",
                description = "Test Description3",
                keywords = listOf("APOLLO 11 FLIGHT", "MOON", "LUNAR SURFACE"),
                media_type = "audio",
                title = "Test Title",
                nasa_id = "as11-40-5874",
                secondary_creator = "Test Creator",
                description_508 = "Test Description508"

            )

        private val fakeLinkObject01 =
            Link(
                href = "https://images-assets.nasa.gov/image/as11-40-5874/as11-40-5874~thumb.jpg",
                rel = "preview",
                render = "image"
            )

        private val fakeLinkObject02 =
            Link(
                href = "http://images-assets.nasa.gov/video/Space-Exploration-Video-1/Space-Exploration-Video-1~orig.mp4",
                rel = "preview",
                render = "video"
            )

        private val fakeLinkObject03 =
            Link(
                href = "http://images-assets.nasa.gov/audio/367-AAA/367-AAA~orig.wav",
                rel = "preview",
                render = "audio"
            )

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