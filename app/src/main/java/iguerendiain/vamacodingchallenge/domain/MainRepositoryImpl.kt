package iguerendiain.vamacodingchallenge.domain

import iguerendiain.vamacodingchallenge.api.MainAPI
import iguerendiain.vamacodingchallenge.domain.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
//    private val mainDAO: MainDAO,
    private val api: MainAPI
): MainRepository {


}