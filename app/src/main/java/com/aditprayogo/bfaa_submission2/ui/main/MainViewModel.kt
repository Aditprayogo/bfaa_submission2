package com.aditprayogo.bfaa_submission2.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditprayogo.bfaa_submission2.core.state.LoaderState
import com.aditprayogo.bfaa_submission2.core.state.ResultState
import com.aditprayogo.bfaa_submission2.data.responses.SearchResponseItem
import com.aditprayogo.bfaa_submission2.domain.UserUseCase
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    /**
     * State
     */
    private val _state = MutableLiveData<LoaderState>()
    val state: LiveData<LoaderState>
        get() = _state

    /**
     * error
     */
    private val _error = MutableLiveData<String>()

    /**
     * Network Error
     */
    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean>
        get() = _networkError

    /**
     * Handle result from api
     */
    private val _resultFromApi = MutableLiveData<List<SearchResponseItem>>()
    val resultFromApi: LiveData<List<SearchResponseItem>>
        get() = _resultFromApi

    fun getUsersFromApi(query : String) {
        _state.value = LoaderState.ShowLoading
        viewModelScope.launch {
            val result = userUseCase.getUserFromApi(query)
            _state.value = LoaderState.HideLoading
            when(result) {
                is ResultState.Success -> _resultFromApi.postValue(result.data?.items)
                is ResultState.Error -> _error.postValue(result.error)
                is ResultState.NetworkError -> _networkError.postValue(true)
            }
        }
    }


}