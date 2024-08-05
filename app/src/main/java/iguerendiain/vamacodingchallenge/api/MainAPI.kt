package iguerendiain.vamacodingchallenge.api

import iguerendiain.vamacodingchallenge.model.Album
import iguerendiain.vamacodingchallenge.model.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainAPI{

    @GET("{country}/music/most-played/{limit}/albums.json")
    suspend fun downloadMostPlayedAlbums(
        @Path("country") country: String? = "us",
        @Path("limit") limit: Int? = 100
    ): Response<ResponseBody<Album>>

}