package iguerendiain.vamacodingchallenge.di

//import android.app.Application
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import nacholab.themovies.storage.MainDAO
//import nacholab.themovies.storage.MainDB
//import nacholab.themovies.storage.MainDBBuilder
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DBModule {
//
//    @Provides
//    @Singleton
//    fun provideMainDB(app: Application): MainDB {
//        return MainDBBuilder.build(app)
//    }
//
//    @Provides
//    fun provideMainDAO(db: MainDB): MainDAO {
//        return db.mainDao()
//    }
//}