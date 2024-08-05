package iguerendiain.vamacodingchallenge.api

import iguerendiain.vamacodingchallenge.model.Album
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MainAPI{

    @GET("{country}/music/most-played/{limit}/albums.json")
    suspend fun downloadMostPlayedAlbums(
        @Query("country") country: String? = "us",
        @Query("limit") limit: Int? = 100
    ): Response<Response<Album>>

}