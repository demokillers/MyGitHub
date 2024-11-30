package com.example.mygithub.utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

object RetrofitUtil {
    private val MEDIA_TYPE_JSON = "application/json;charset=UTF-8".toMediaTypeOrNull()

    /**
     * 通过参数 Map 合集
     * @param paramsMap
     * @return
     */
    fun createJsonRequest(paramsMap: HashMap<String?, Any?>?): RequestBody {
        val gson = Gson()
        val strEntity = gson.toJson(paramsMap)
        return RequestBody.create(MEDIA_TYPE_JSON, strEntity)
    }

    /**
     * 通过参数 Map 合集
     * @return
     */
    fun createJsonRequest(params: Any?): RequestBody {
        val gson = Gson()
        val strEntity = gson.toJson(params)
        return RequestBody.create(MEDIA_TYPE_JSON, strEntity)
    }
}
