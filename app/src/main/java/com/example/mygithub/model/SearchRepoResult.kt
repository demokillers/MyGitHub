package com.example.mygithub.model

import androidx.annotation.Keep

@Keep
data class SearchRepoResult(
    val incomplete_results: Boolean,
    val items: MutableList<RepoEntity>,
    val total_count: Int
)