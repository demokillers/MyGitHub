package com.example.mygithub.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.MyGitHub.R
import com.example.mygithub.base.BaseViewModel
import com.example.mygithub.model.RepoEntity
import com.example.mygithub.mvvm.MutablePublishData

class SearchViewModel : BaseViewModel() {

    val reposListLD = MutableLiveData<List<RepoEntity>>()

    val toastPD = MutablePublishData<Int>()

    private var pageIndex = 0

    /**
     * 搜索
     */
    fun searchPos(query: String, filterPython: Boolean) {
        if (query.isEmpty()) {
            toastPD.publish(R.string.queryEmpty)
            return
        }
        showProgressLD.value = true
        launch(
            block = {
                val finalQuery = query + if (filterPython) "+language:python" else ""
                //demo没有实现分页，只拉取第一页
                val searchRepoResult = apiService.searchPos(finalQuery, pageIndex)
                reposListLD.postValue(searchRepoResult.items)
                showProgressLD.value = false
            },
            error = {
                showProgressLD.value = false
            }
        )
    }
}