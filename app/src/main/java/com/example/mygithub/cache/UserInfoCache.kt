package com.example.mygithub.cache

import com.example.mygithub.MyApplication
import com.example.mygithub.model.UserInfo
import com.example.mygithub.utils.clearSpValue
import com.example.mygithub.utils.getSpValue
import com.example.mygithub.utils.putSpValue
import com.google.gson.Gson

object UserInfoCache {

    private val SP_USER_INFO = "sp_user_info_cache"
    private val KEY_USER_INFO = "userInfoCache"

    /**
     * 获取本地sp存储的用户信息
     */
    fun getUserInfo(): UserInfo? {
        val userInfoStr = getSpValue(SP_USER_INFO, MyApplication.instance, KEY_USER_INFO, "")
        return if (userInfoStr.isNotEmpty()) {
            Gson().fromJson(userInfoStr, UserInfo::class.java)
        } else {
            null
        }
    }

    /**
     * 设置用户信息、保存本地sp
     */
    fun setUserInfo(userInfo: UserInfo) =
        putSpValue(
            SP_USER_INFO,
            MyApplication.instance,
            KEY_USER_INFO,
            Gson().toJson(userInfo)
        )

    /**
     * 清除用户信息
     */
    fun clearUserInfo() {
        clearSpValue(SP_USER_INFO, MyApplication.instance)
    }
}