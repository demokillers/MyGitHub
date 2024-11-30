package com.example.mygithub.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.MyGitHub.BuildConfig
import com.example.MyGitHub.R
import com.example.mygithub.base.BaseViewModel
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.mvvm.MutablePublishData
import com.example.mygithub.utils.RetrofitUtil
import com.example.mygithub.utils.toActualToken

class RepoContentViewModel : BaseViewModel() {

    val reposLD = MutableLiveData<RepoEntity>()

    val toastPD = MutablePublishData<Int>()

    /**
     * 获取仓库详情
     */
    fun getPos(data: RepoEntity?) {
        data ?: return
        showProgressLD.value = true
        launch(
            block = {
                val repos = apiService.getPos(data.owner.login.orEmpty(), data.name.orEmpty())
                reposLD.value = repos
                showProgressLD.value = false
            },
            error = {
                showProgressLD.value = false
            }
        )
    }

    /**
     * 新增Issues
     */
    fun addIssues(data: RepoEntity?, issueTitle: String, issueBody: String) {
        data ?: return
        if (issueTitle.isEmpty()) {
            toastPD.publish(R.string.issuesTitleEmpty)
            return
        }
        showProgressLD.value = true
        launch(
            block = {
                val repos = apiService.addIssues(
                    BuildConfig.USER_ACCESS_TOKEN.toActualToken(),
                    data.owner.login.orEmpty(),
                    data.name.orEmpty(),
                    RetrofitUtil.createJsonRequest(
                        hashMapOf(
                            Pair("title", issueTitle),
                            Pair("body", issueBody)
                        )
                    )
                )
                showProgressLD.value = false
                toastPD.publish(R.string.addIssuesSuccess)
            },
            error = {
                showProgressLD.value = false
            }
        )
    }
}