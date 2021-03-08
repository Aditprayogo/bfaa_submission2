package com.aditprayogo.bfaa_submission2.data.responses


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<SearchResponseItem>?,
    @SerializedName("total_count")
    val totalCount: Int?
)