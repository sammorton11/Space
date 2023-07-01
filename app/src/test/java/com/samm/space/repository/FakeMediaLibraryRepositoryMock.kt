package com.samm.space.repository

import com.samm.space.core.Resource
import com.samm.space.features.favorites_page.data.database.SpaceExplorerDatabase
import com.samm.space.features.nasa_media_library_page.data.network.MetadataApi
import com.samm.space.features.nasa_media_library_page.data.network.NasaApi
import com.samm.space.features.nasa_media_library_page.domain.models.Collection
import com.samm.space.features.nasa_media_library_page.domain.models.Data
import com.samm.space.features.nasa_media_library_page.domain.models.Item
import com.samm.space.features.nasa_media_library_page.domain.models.Link
import com.samm.space.features.nasa_media_library_page.domain.models.NasaLibraryResponse
import com.samm.space.features.nasa_media_library_page.domain.repository.MediaLibraryRepository
import com.samm.space.util.FakeResponseTrigger
import com.samm.space.util.successOrError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class FakeMediaLibraryRepositoryMock @Inject constructor (
    private val api: NasaApi,
    private val apiMetaData: MetadataApi,
    private val database: SpaceExplorerDatabase
): MediaLibraryRepository {

    override fun getAllFavorites(): Flow<List<Item>> {
        return database.myDao().getAllLibraryFavorites()
    }

    override suspend fun insertFavorite(item: Item) {
        database.myDao().insertFavorite(item)
    }

    override suspend fun deleteFavorite(item: Item) {
        database.myDao().deleteFavorite(item)
    }

    override suspend fun updateFavorite(itemId: Int, isFavorite: Boolean) {
        database.myDao().updateFavoriteState(itemId, if (isFavorite) 1 else 0)
    }

    override suspend fun getVideoData(url: String): String? {
        return apiMetaData.fetchData(url)
    }

    override suspend fun getData(query: String): NasaLibraryResponse? {
        return api.fetchData(query)
    }

    override fun searchImageVideoLibrary(query: String): Flow<Resource<NasaLibraryResponse?>> = flow {
        try {
            when (successOrError(query)) {
                FakeResponseTrigger.SUCCESS -> emit(Resource.Success(FakeLibraryApiResponse.response))
                FakeResponseTrigger.HTTP_ERROR -> throw HttpException(Response.error<Any>(400,
                    "Bad Request".toResponseBody(null)
                ))
                FakeResponseTrigger.IO_ERROR -> throw IOException("Network connection error")
            }
        } catch (e: Exception) {
            // Emit the error response based on the exception type
            when (e) {
                is HttpException -> emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
                is IOException -> emit(Resource.Error(e.localizedMessage ?: "Check Internet Connection"))
                else -> emit(Resource.Error("Unknown Error"))
            }
        }
    }


    override fun savedQueryFlow(): Flow<String?> = flow {
        emit("Fake saved query")
    }

    override fun mediaDataFlow(url: String) = flow {
        try {
            emit(Resource.Loading())
            val response = getVideoData(url)
            emit(Resource.Success(response))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }
}


object FakeLibraryApiResponse {
    val response = NasaLibraryResponse(
        collection = Collection(
            version = "1.0",
            href = "http://images-api.nasa.gov/search?&q=Mars",
            items = listOf(
                Item(
                    href = "https://images-assets.nasa.gov/image/NHQ201905310026/collection.json",
                    data = listOf(Data(
                        "HQ",
                        "Mars Celebration",
                        "NHQ201905310033",
                        "2019-05-31T00:00:00Z",
                       listOf(""),
                        "image",
                        null,
                        null,
                        "The Mars celebration Friday, May 31, 2019, in Mars, Pennsylvania. NASA is in the small town to celebrate Mars exploration and share the agency’s excitement about landing astronauts on the Moon in five years. The celebration includes a weekend of Science, Technology, Engineering, Arts and Mathematics (STEAM) activities. Photo Credit: (NASA/Bill Ingalls)",

                    )),
                    links = listOf(Link(
                        href = "https://images-assets.nasa.gov/image/NHQ201905310026/NHQ201905310026~thumb.jpg",
                        rel = "preview",
                        render = "image"
                    ))
                ),
                Item(
                    href = "https://images-api.nasa.gov/search?q=mars&media_type=image,audio,video",
                    data = listOf(Data(
                        "HQ",
                        "Importance of Human Space Exploration: Drew Feustel",
                        "NHQ201905310033",
                        "2021-03-26T16:57:17Z",
                        listOf("Drew Feustel",
                            "NASA astronaut",
                            "human space exploration",
                            "space",
                            "exploration",
                            "ISS",
                            "International Space Station"),
                        "audio",
                        null,
                        null,
                        "NASA astronaut Drew Feustel talks about the importance of human space exploration, and what it's like to live and work in space.",

                        )),
                    links = listOf(Link(
                        href = "https://images-assets.nasa.gov/audio/NASAEpx/NASAEpx~128k.mp3",
                        rel = "self",
                        render = "audio"
                    ))
                ),
                Item(
                    href = "https://images-assets.nasa.gov/video/NHQ_2019_0311_Go Forward to the Moon/collection.json",
                    data = listOf(Data(
                        "HQ",
                        "Go Forward to the Moon",
                        "NHQ_2019_0311_Go Forward to the Moon",
                        "2019-05-31T00:00:00Z",
                        listOf(""),
                        "image",
                        null,
                        null,
                        "NASA is going to the Moon and on to Mars, in a measured, sustainable way. Working with U.S. companies and international partners, NASA will push the boundaries of human exploration forward to the Moon. NASA is working to establish a permanent human presence on the Moon within the next decade to uncover new scientific discoveries and lay the foundation for private companies to build a lunar economy.  Right now, NASA is taking steps to begin this next era of exploration. #Moon2Mars  Learn more at: https://www.nasa.gov/moontomars",

                        )),
                    links = listOf(Link(
                        href = "https://images-assets.nasa.gov/video/NHQ_2019_0311_Go Forward to the Moon/NHQ_2019_0311_Go Forward to the Moon~thumb.jpg",
                        rel = "preview",
                        render = "image"
                    ))
                ))
        )
    )
}