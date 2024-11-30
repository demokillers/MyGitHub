package com.example.mygithub.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.MyGitHub.BuildConfig
import com.example.mygithub.base.BaseViewModel
import com.example.mygithub.cache.UserInfoCache
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.utils.toActualToken

class LoginViewModel : BaseViewModel() {

    val loginResult = MutableLiveData<Boolean>()
    val reposListLD = MutableLiveData<MutableList<RepoEntity>>()

    /**
     * 登录
     */
    fun login() {
        showProgressLD.value = true
        launch(
            block = {
                val userInfo =
                    apiService.fetchUserOwner(BuildConfig.USER_ACCESS_TOKEN.toActualToken())
                UserInfoCache.setUserInfo(userInfo)
                loginResult.postValue(true)
                showProgressLD.value = false
            },
            error = {
                showProgressLD.value = false
            }
        )
    }

    /**
     * 获取公开仓库
     */
    fun getPros() {
        launch(
            block = {
                val reposList = apiService.getPos()
                reposListLD.postValue(reposList)
                finishRefreshOrLoadMore.publish(true)
            },
            error = {
                finishRefreshOrLoadMore.publish(true)
            }
        )
    }
}