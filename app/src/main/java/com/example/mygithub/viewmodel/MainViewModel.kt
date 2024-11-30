package com.example.mygithub.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mygithub.base.BaseViewModel
import com.example.mygithub.model.RepoEntity

class MainViewModel : BaseViewModel() {

    val reposListLD = MutableLiveData<MutableList<RepoEntity>>()

    /**
     * 获取个人仓库
     */
    fun getPros(userName: String) {
        launch(
            block = {
                val reposList = apiService.getPos(userName)
                reposListLD.postValue(reposList)
                finishRefreshOrLoadMore.publish(true)
            },
            error = {
                finishRefreshOrLoadMore.publish(true)
            }
        )
    }
}