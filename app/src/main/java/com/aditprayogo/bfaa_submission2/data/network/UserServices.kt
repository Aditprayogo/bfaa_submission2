package com.aditprayogo.bfaa_submission2.data.network

import com.aditprayogo.bfaa_submission2.data.responses.DetailUserResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowingResponse
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserServices {

    /**
     * Endpoint Search User
     */
    @GET("search/users?")
    suspend fun getSearchUsers(
        @Query("q") q : String
    ) : Response<SearchResponse>

    /**
     * Endpoint detail user
     */
    @GET("users/{username}")
    @Headers("Authorization: token cdad1610fc462061169b4153123e0efde13c37da")
    suspend fun getDetailUser(
        @Path("username") username : String
    ) : Response<DetailUserResponse>

    /**
     * Endpoint followers
     */
    @GET("users/{username}/followers")
    suspend fun getFollowerUser(
        @Path("username") username: String
    ) : Response<FollowerResponse>

    /**
     * Endpoint following
     */
    @GET("users/{username}/following")
    suspend fun getFollowingUser(
        @Path("username") username: String
    ) : Response<FollowingResponse>


}