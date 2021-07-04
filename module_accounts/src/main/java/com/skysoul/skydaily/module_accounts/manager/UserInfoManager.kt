package com.skysoul.skydaily.module_accounts.manager

import androidx.lifecycle.MutableLiveData
import com.skysoul.skydaily.module_accounts.model.beans.User
import com.skysoul.skydaily.module_accounts.utils.SPUtil

/**
 *@author skysoul
 *@date 2021/5/16
 *@description
 */
class UserInfoManager private constructor() {

    var user: User? = null
        private set

    companion object {
        val instance: UserInfoManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { UserInfoManager() }
    }

    fun saveUser(user: User?) {
        if(user==null){
            clearUser()
            return
        }
        SPUtil.apply {
            instance.put(LAST_USER_ID, user.userId)
            instance.put(LAST_USER_NAME, user.userName)
        }
        this.user = user
    }

    fun clearUser() {
        //只清除id，表示退出登录，不清除userName，下次开启登录页面时可以展示上次的用户名，只需要输入密码
        SPUtil.apply {
            instance.put(LAST_USER_ID, 0L)
        }
        this.user = null
    }

    fun getUserId(): Long {
        return user?.userId ?: 0L
    }

    fun getUserName(): String {
        return user?.userName ?: ""
    }
}