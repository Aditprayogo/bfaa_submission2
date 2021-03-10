package com.aditprayogo.bfaa_submission2.ui.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.loader.content.Loader
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.state.ResultState
import com.aditprayogo.bfaa_submission2.data.responses.DetailUserResponse
import com.aditprayogo.bfaa_submission2.domain.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    /**
     * State
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * Error
     */
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    /**
     * Network Error
     */
    private val _networkError = MutableLiveData<Boolean>()

    /**
     *  User Detail Resul Remote
     */
    private val _resultUserDetail = MutableLiveData<DetailUserResponse>()
    val resultUserDetail: LiveData<DetailUserResponse>
        get() = _resultUserDetail

    /**
     * Remote
     */
    fun getUserDetailFromApi(username : String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = userUseCase.getUserDetailFromApi(username)
            _state.value = LoaderState.HideLoading
            when(result) {
                is ResultState.Success -> _resultUserDetail.postValue(result.data)
                is ResultState.Error -> _error.postValue(result.error)
                is ResultState.NetworkError -> _networkError.postValue(true)
            }
        }
    }



}