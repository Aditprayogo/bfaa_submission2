package com.aditprayogo.bfaa_submission2.ui.Follower

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.state.ResultState
import com.aditprayogo.bfaa_submission2.data.responses.FollowerResponseItem
import com.aditprayogo.bfaa_submission2.domain.UserUseCase
import kotlinx.coroutines.launch

class FollowerViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    /**
     * Loader state
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Error state
     */
    private val _error = MutableLiveData<String>()

    /**
     *  Network Error state
     */
    private val _networkError = MutableLiveData<Boolean>()

    /**
     * Get result user follower
     */
    private val _resultUserFollower = MutableLiveData<List<FollowerResponseItem>>()
    val resultUserFollower: LiveData<List<FollowerResponseItem>>
        get() = _resultUserFollower

    fun getUserFollowers(username : String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = userUseCase.getUserFollowers(username)
            _state.value = LoaderState.HideLoading
            when(result) {
                is ResultState.Success -> _resultUserFollower.postValue(result.data)
                is ResultState.Error -> _error.postValue(result.error)
                is ResultState.NetworkError -> _networkError.postValue(true)
            }
        }
    }

}