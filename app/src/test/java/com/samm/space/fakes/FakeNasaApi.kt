package com.samm.space.fakes

import com.samm.space.features.nasa_media_library_page.data.network.NasaApi
import com.samm.space.features.nasa_media_library_page.domain.models.NasaLibraryResponse
import com.samm.space.repository.FakeLibraryApiResponse

// Fake implementation of NasaApi
class FakeNasaApi : NasaApi {
    override suspend fun fetchData(query: String): NasaLibraryResponse {
        // Return a predefined response suitable for testing
        // You can define different responses based on the input query if needed
        return FakeLibraryApiResponse.response
    }
}