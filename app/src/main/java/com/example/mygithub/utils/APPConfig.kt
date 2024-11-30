package com.example.mygithub.utils

object APPConfig {
    const val GITHUB_API_BASE_URL: String = "https://api.github.com/"

    const val API_CONNECT_TIME_OUT = 10L

    const val API_READ_TIME_OUT = 10L

    const  val API_WRITE_TIME_OUT = 10L

    const val PER_PAGE = 100
}

fun String.toActualToken(): String {
    return "Bearer $this"
}