package com.skysoul.skydaily.module_accounts.activities.login

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.radiance.androidbase.applibcore.RBaseViewModel
import com.skysoul.skydaily.exts.isPassword
import com.skysoul.skydaily.exts.isUserName
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import com.skysoul.skydaily.module_accounts.model.repository.IUserRepository
import com.skysoul.skydaily.module_accounts.model.repository.local.UserRepositoryLocal
import com.skysoul.skydaily.module_accounts.model.repository.resultbeans.LoginErr
import com.skysoul.skydaily.module_accounts.utils.SPUtil

/**
 *@author skysoul
 *@date 2021/5/16
 *@description
 */
class LoginViewModel(app: Application) : RBaseViewModel(app) {
    private val userRepository: IUserRepository = UserRepositoryLocal()

    var uiData: LoginUIBean = LoginUIBean()

    /**
     *1登录成功
     */
    var jumpType: MutableLiveData<Int> = MutableLiveData()

    override fun init() {
    }

    fun getLastUser() {
        showLastUsername()
        val lastUserId = SPUtil.instance.getLong(SPUtil.LAST_USER_ID)
        if (lastUserId <= 0) {
            return
        }
        launchOnNet({
            userRepository.getUserById(lastUserId)?.also {
                UserInfoManager.instance.saveUser(it)
                if(!it.nickName.isNullOrEmpty()) {
                    jumpType.postValue(1)
                }
            }
        })
    }

    private fun showLastUsername() {
        SPUtil.instance.getString(SPUtil.LAST_USER_NAME, null)?.run {
            uiData.etUsername.postValue(this)
        }
    }

    fun doLogin(){
        launchOnNet({
            if(!checkBase()){
                return@launchOnNet
            }
            var username = uiData.etUsername.value
            var password = uiData.etPassword.value
            val result = userRepository.checkPasswordWithType(0,username!!,password!!)
            result.run {
                when {
                    user != null ->{
                        UserInfoManager.instance.saveUser(user!!)
                        jumpType.postValue(1)
                    }
                    errorType < 0 -> {
                        when {
                            errorType == LoginErr.noUser -> uiData.etUsernameError.postValue("用户不存在")
                            leftTimes > 0 -> uiData.etPasswordError.postValue("密码错误,还可以尝试${leftTimes}次")
                            !TextUtils.isEmpty(nextTryTime) -> {
                                uiData.etPasswordError.postValue("密码错误,请在${nextTryTime}小时后尝试")
                            }
                        }
                    }
                }
            }
        })
    }

    private fun checkBase(): Boolean {
        uiData.etUsernameError.postValue("")
        uiData.etPasswordError.postValue("")
        var userName = uiData.etUsername.value ?: ""
        var password = uiData.etPassword.value ?: ""
        if (!userName.isUserName()) {
            uiData.etUsernameError.postValue("用户名4-20位")
            return false
        }
        if (!password.isPassword()) {
            uiData.etPasswordError.postValue( "密码不合法，6-15位，只包含大小写，数字，下划线")
            return false
        }
        return true
    }
}