package com.aditprayogo.bfaa_submission2.domain

import com.aditprayogo.bfaa_submission2.core.state.ResultState
import com.aditprayogo.bfaa_submission2.core.util.safeApiCall
import com.aditprayogo.bfaa_submission2.data.responses.DetailUserResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponse
import com.aditprayogo.bfaa_submission2.data.responses.FollowingResponse
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponse
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    /**
     * Remote
     */
    suspend fun getUserFromApi(username : String) : ResultState<SearchResponse> {
        return safeApiCall {
            val response = userRepository.getUserFromApi(username)
            try {
                ResultState.Success(response.body())
            } catch (e : Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }

    suspend fun getUserDetailFromApi(username : String) : ResultState<DetailUserResponse> {
        return safeApiCall {
            val response = userRepository.getDetailUserFromApi(username)
            try {
                ResultState.Success(response.body())
            } catch (e : Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }

    suspend fun getUserFollowers(username : String) : ResultState<FollowerResponse> {
        return safeApiCall {
            val response = userRepository.getUserFollowers(username)
            try {
                ResultState.Success(response.body())
            } catch (e : Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }

    suspend fun getUserFollowing(username : String) : ResultState<FollowingResponse> {
        return safeApiCall {
            val response = userRepository.getUserFollowing(username)
            try {
                ResultState.Success(response.body())
            } catch (e : Exception) {
                ResultState.Error(e.localizedMessage, response.code())
            }
        }
    }
}