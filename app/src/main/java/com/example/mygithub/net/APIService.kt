package com.example.mygithub.net

import com.example.mygithub.model.RepoEntity
import com.example.mygithub.model.SearchRepoResult
import com.example.mygithub.model.UserInfo
import com.example.mygithub.utils.APPConfig
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    /**
     * 登录
     */
    @GET("user")
    suspend fun fetchUserOwner(@Header("Authorization") authorization: String): UserInfo

    /**
     * 获取某用户所有仓库
     */
    @GET("users/{username}/repos")
    suspend fun getPos(
        @Path("username") username: String,
        @Query("per_page") perPage: Int = APPConfig.PER_PAGE
    ): MutableList<RepoEntity>

    /**
     * 获取公开仓库
     */
    @GET("repositories")
    suspend fun getPos(): MutableList<RepoEntity>

    /**
     * 获取某用户某个仓库
     */
    @GET("repos/{username}/{repo}")
    suspend fun getPos(@Path("username") username: String, @Path("repo") repo: String): RepoEntity

    /**
     * 新增issues
     */
    @POST("repos/{username}/{repo}/issues")
    suspend fun addIssues(
        @Header("Authorization") authorization: String,
        @Path("username") username: String, @Path("repo") repo: String,
        @Body body: RequestBody?
    ): RepoEntity

    /**
     * 搜索仓库
     */
    @GET("search/repositories")
    suspend fun searchPos(
        @Query(value = "q", encoded = true) query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = APPConfig.PER_PAGE,
        @Query("sort") sort: String = "stars"
    ): SearchRepoResult

}