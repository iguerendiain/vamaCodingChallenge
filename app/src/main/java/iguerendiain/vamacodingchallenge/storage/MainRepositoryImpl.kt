package iguerendiain.vamacodingchallenge.storage

import iguerendiain.vamacodingchallenge.api.MainAPI
import iguerendiain.vamacodingchallenge.domain.MainRepository
import iguerendiain.vamacodingchallenge.model.Album
import io.realm.kotlin.Realm
import io.realm.kotlin.query.Sort
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val realm: Realm,
    private val api: MainAPI
): MainRepository {
    override suspend fun downloadAlbums(country: String, limit: Int): Result<List<Album>> {
        return try {
            val albumAPIResponse = api.downloadMostPlayedAlbums(country, limit)

            if (albumAPIResponse.isSuccessful)
                Result.success(albumAPIResponse.body()?.feed?.results?: listOf())
            else
                Result.failure(Exception(albumAPIResponse.errorBody()?.string()))
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun storeLocalAlbums(albums: List<Album>) {
        realm.write { albums.forEach { copyToRealm(it) } }
    }

    override suspend fun getLocalAlbums(): List<Album> {
        return realm
            .query(Album::class)
            .sort("releaseDate", Sort.DESCENDING)
            .find()
    }

    override suspend fun clearLocalAlbums() {
        realm.write { delete(query(Album::class).find()) }
    }

}