package com.aditprayogo.bfaa_submission2.ui.Following

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.state.ResultState
import com.aditprayogo.bfaa_submission2.data.responses.FollowingResponseItem
import com.aditprayogo.bfaa_submission2.domain.UserUseCase
import kotlinx.coroutines.launch

class FollowingViewModel @ViewModelInject constructor(
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
    private val _networkError = MutableLiveData<Boolean>()

    /**
     * State result user Following
     */
    private val _resultUserFollowing = MutableLiveData<List<FollowingResponseItem>>()
    val resultUserFollowing: LiveData<List<FollowingResponseItem>>
        get() = _resultUserFollowing

    fun getUserFollowing(username : String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = userUseCase.getUserFollowing(username)
            _state.value = LoaderState.HideLoading
            when(result) {
                is ResultState.Success -> _resultUserFollowing.postValue(result.data)
                is ResultState.Error -> _error.postValue(result.error)
                is ResultState.NetworkError -> _networkError.postValue(true)
            }
        }
    }


}