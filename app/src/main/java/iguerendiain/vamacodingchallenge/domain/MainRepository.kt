package iguerendiain.vamacodingchallenge.domain

import iguerendiain.vamacodingchallenge.model.Album

interface MainRepository {

    suspend fun downloadAlbums(country: String, limit: Int): Result<List<Album>>

    suspend fun storeLocalAlbums(albums: List<Album>)

    suspend fun getLocalAlbums(): List<Album>

    suspend fun clearLocalAlbums()

}