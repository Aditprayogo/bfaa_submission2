package com.aditprayogo.bfaa_submission2.core.state

sealed class LoaderState {
    object ShowLoading : LoaderState()
    object HideLoading : LoaderState()
}