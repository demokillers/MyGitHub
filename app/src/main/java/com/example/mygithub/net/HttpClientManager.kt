package com.example.mygithub.net

import com.example.mygithub.utils.APPConfig

object HttpClientManager {
    fun getClientAPIBASE(): RetrofitClient {
        return RetrofitClient(APPConfig.GITHUB_API_BASE_URL)
    }
}