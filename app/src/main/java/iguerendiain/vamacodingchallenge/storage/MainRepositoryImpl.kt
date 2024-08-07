package iguerendiain.vamacodingchallenge.storage

import iguerendiain.vamacodingchallenge.api.MainAPI
import iguerendiain.vamacodingchallenge.domain.MainRepository
import iguerendiain.vamacodingchallenge.model.Album
import iguerendiain.vamacodingchallenge.model.Feed
import iguerendiain.vamacodingchallenge.storage.model.AlbumRealm
import iguerendiain.vamacodingchallenge.storage.model.FeedRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val realm: Realm,
    private val api: MainAPI
): MainRepository {

    override suspend fun downloadAlbums(country: String, limit: Int): Result<Feed<Album>> {
        return try {
            val albumAPIResponse = api.downloadMostPlayedAlbums(country, limit)

            if (albumAPIResponse.isSuccessful) {
                val feed = albumAPIResponse.body()?.feed
                if (feed != null) Result.success(feed)
                else Result.failure(Exception("Empty feed"))
            } else
                Result.failure(
                    APIErrorException(
                        APIErrorInfo(
                            type = APIErrorInfo.UNKNOWN,
                            errorBody = albumAPIResponse.errorBody()?.string(),
                            statusCode = albumAPIResponse.code(),
                            exception = null
                        )
                    )
                )
        }catch (e: Exception){
            val errorType = when (e) {
                is IOException -> APIErrorInfo.NETWORK
                is RuntimeException -> APIErrorInfo.PARSE
                else -> APIErrorInfo.UNKNOWN
            }

            Result.failure(APIErrorException(APIErrorInfo(type = errorType, exception = e)))
        }
    }

    override suspend fun storeLocalAlbums(albums: List<Album>) {
        realm.write { albums.forEach { copyToRealm(
            instance = AlbumRealm.fromModel(it),
            updatePolicy = UpdatePolicy.ALL
        ) } }
    }

    override suspend fun getLocalAlbums(): List<Album> {
        return realm
            .query(AlbumRealm::class)
            .find()
            .map { it.toModel() }
    }

    override suspend fun clearLocalAlbums() {
        realm.write { delete(query(AlbumRealm::class).find()) }
    }

    override suspend fun <T> storeFeedData(feed: Feed<T>) {
        realm.write {
            copyToRealm(
                instance = FeedRealm().apply { copyright = feed.copyright },
                updatePolicy = UpdatePolicy.ALL
            )
        }
    }

    override suspend fun <T> getFeedData(): Feed<T>? {
        return realm
            .query(FeedRealm::class)
            .first()
            .find()
            ?.copyright
            ?.let { Feed(copyright = it) }
    }
}