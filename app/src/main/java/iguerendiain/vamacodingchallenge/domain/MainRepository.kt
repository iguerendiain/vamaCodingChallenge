package iguerendiain.vamacodingchallenge.domain

import iguerendiain.vamacodingchallenge.model.Album
import iguerendiain.vamacodingchallenge.model.Feed

interface MainRepository {

    suspend fun downloadAlbums(country: String, limit: Int): Result<Feed<Album>>

    suspend fun storeLocalAlbums(albums: List<Album>)

    suspend fun getLocalAlbums(): List<Album>

    suspend fun clearLocalAlbums()

    suspend fun <T> storeFeedData(feed: Feed<T>)

    suspend fun <T> getFeedData(): Feed<T>?
}