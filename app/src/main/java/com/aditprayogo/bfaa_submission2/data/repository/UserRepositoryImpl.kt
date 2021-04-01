package com.aditprayogo.bfaa_submission2.data.repository

import com.aditprayogo.bfaa_submission2.data.network.UserServices
import com.aditprayogo.bfaa_submission2.data.responses.DetailUserResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowingResponse
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userServices: UserServices
) : UserRepository {
    override suspend fun getUserFromApi(username: String): Response<SearchResponse> {
        return userServices.getSearchUsers(username)
    }

    override suspend fun getDetailUserFromApi(username: String): Response<DetailUserResponse> {
        return userServices.getDetailUser(username)
    }

    override suspend fun getUserFollowers(username: String): Response<FollowerResponse> {
        return userServices.getFollowerUser(username)
    }

    override suspend fun getUserFollowing(username: String): Response<FollowingResponse> {
        return userServices.getFollowingUser(username)
    }
}