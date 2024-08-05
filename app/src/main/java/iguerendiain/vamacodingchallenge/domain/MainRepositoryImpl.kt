package iguerendiain.vamacodingchallenge.domain

import iguerendiain.vamacodingchallenge.api.MainAPI
import iguerendiain.vamacodingchallenge.model.Album
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
//    private val mainDAO: MainDAO,
    private val api: MainAPI
): MainRepository {
    override suspend fun downloadAlbums(country: String, limit: Int): Result<List<Album>> {
        return Result.success(listOf())
    }

    override suspend fun storeAlbums(albums: List<Album>) {

    }

    override suspend fun getLocalAlbums(): List<Album> {
        return listOf()
    }

}