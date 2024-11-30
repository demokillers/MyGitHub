package com.example.mygithub.base

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.MyGitHub.R
import com.example.mygithub.MyApplication
import com.example.mygithub.mvvm.MutablePublishData
import com.example.mygithub.net.APIService
import com.example.mygithub.net.HttpClientManager
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

typealias Block<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit


open class BaseViewModel : ViewModel() {

    val apiService = HttpClientManager.getClientAPIBASE()
        .createService(APIService::class.java)

    val finishRefreshOrLoadMore: MutablePublishData<Boolean> = MutablePublishData()

    val showProgressLD: MutableLiveData<Boolean> = MutableLiveData()

    fun launch(
        block: Block<Unit>, error: Error? = null, showCommonErrorToast: Boolean = true
    ): Job {
        return viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                error?.invoke(e)
                if (e !is CancellationException && showCommonErrorToast) {
                    //取消任务不需要提示
                    Toast.makeText(
                        MyApplication.instance,
                        R.string.commonError,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}