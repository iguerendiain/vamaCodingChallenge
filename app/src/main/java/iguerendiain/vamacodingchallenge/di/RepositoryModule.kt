package iguerendiain.vamacodingchallenge.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iguerendiain.vamacodingchallenge.domain.MainRepository
import iguerendiain.vamacodingchallenge.storage.MainRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun repositoryBinding(mainRepositoryImpl: MainRepositoryImpl): MainRepository

}