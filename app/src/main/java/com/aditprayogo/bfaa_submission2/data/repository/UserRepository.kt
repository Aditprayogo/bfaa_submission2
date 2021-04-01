package com.aditprayogo.bfaa_submission2.data.repository

import com.aditprayogo.bfaa_submission2.data.responses.DetailUserResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowingResponse
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponse
import retrofit2.Response

interface UserRepository {

    /**
     * Remote
     */
    suspend fun getUserFromApi(username : String) : Response<SearchResponse>

    suspend fun getDetailUserFromApi(username : String) : Response<DetailUserResponse>

    suspend fun getUserFollowers(username: String) : Response<FollowerResponse>

    suspend fun getUserFollowing(username: String) : Response<FollowingResponse>
}