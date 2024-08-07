package iguerendiain.vamacodingchallenge.api

import iguerendiain.vamacodingchallenge.BuildConfig
import iguerendiain.vamacodingchallenge.model.Album
import iguerendiain.vamacodingchallenge.model.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainAPI{

    @GET("{country}/music/most-played/{limit}/albums.json")
    suspend fun downloadMostPlayedAlbums(
        @Path("country") country: String? = BuildConfig.ALBUM_DOWNLOAD_COUNTRY,
        @Path("limit") limit: Int? = BuildConfig.ALBUM_DOWNLOAD_LIMIT,
    ): Response<ResponseBody<Album>>

}