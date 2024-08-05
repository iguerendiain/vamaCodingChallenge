package iguerendiain.vamacodingchallenge.domain

import iguerendiain.vamacodingchallenge.model.Album

interface MainRepository {

    suspend fun downloadAlbums(country: String, limit: Int): Result<List<Album>>

    suspend fun storeAlbums(albums: List<Album>)

    suspend fun getLocalAlbums(): List<Album>

}