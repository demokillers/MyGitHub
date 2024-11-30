package com.example.mygithub.model

import androidx.annotation.Keep

@Keep
data class UserInfo(
    val id: String?,
    val login: String?,
    val avatar_url: String?,
    val name: String?,
    val public_repos: Int?,
    val public_gists: Int?,
    val followers: Int?,
    val following: Int?,
    val email: String?,
    val blog: String?,
    val company: String?,
    val location: String?,
    val url: String?,
    val bio: String?,
    val node_id: String?,
    val total_private_repos: Int?,
    val html_url: String?
)