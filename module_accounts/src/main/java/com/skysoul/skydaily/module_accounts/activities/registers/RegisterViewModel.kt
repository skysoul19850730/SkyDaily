package com.skysoul.skydaily.module_accounts.activities.registers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.radiance.androidbase.applibcore.RBaseViewModel
import com.skysoul.skydaily.exts.isPassword
import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.constant.Reg
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import com.skysoul.skydaily.module_accounts.model.repository.IUserRepository
import com.skysoul.skydaily.module_accounts.model.repository.local.UserRepositoryLocal

/**
 *@author skysoul
 *@date 2021/5/23
 *@description
 */
class RegisterViewModel(app: Application) : RBaseViewModel(app) {

    val uiData = RegisterUIData()
    var userRepository: IUserRepository = UserRepositoryLocal()

    /**
     *1注册成功
     */
    var jumpType: MutableLiveData<Int> = MutableLiveData()

    override fun init() {

        uiData.etUsername.observe(getLifeOwner()) {
            uiData.etUsernameError.postValue("")
        }
        uiData.etPassword.observe(getLifeOwner()) {
            uiData.etPasswordError.postValue("")
        }
        uiData.etPassword2.observe(getLifeOwner()) {
            uiData.etPasswordError2.postValue("")
        }
    }

    fun register() {

        if (!perCheckUsername() || !perCheckPassword()) {
            return
        }

        launchOnNetSilent({
            val isUsernameExit = userRepository.isUserNameExist(uiData.etUsername.value!!)
            if (isUsernameExit) {
                uiData.etUsernameError.postValue(getString(R.string.register_user_exists))
                return@launchOnNetSilent
            }
            val user = userRepository.registerWith(
                uiData.etUsername.value!!,
                uiData.etPassword.value!!,
                uiData.etPasswordTip.value!!
            )
            UserInfoManager.instance.saveUser(user)
            jumpType.postValue(1)
        },error = {
            toast(it.message?:"")
        })

    }

    private fun perCheckPassword(): Boolean {
        if (uiData.etPassword.value.isNullOrEmpty()) {
            uiData.etPasswordError.postValue(getString(R.string.register_error_password))
            return false
        }
        if (!uiData.etPassword.value!!.isPassword()) {
            uiData.etPasswordError.postValue(getString(R.string.register_error_password))
            return false
        }
        if (uiData.etPassword.value != uiData.etPassword2.value) {
            uiData.etPasswordError2.postValue(getString(R.string.register_error_password_different))
            return false
        }

        return true
    }

    private fun perCheckUsername(): Boolean {
        if (uiData.etUsername.value.isNullOrEmpty()) {
            uiData.etUsernameError.postValue(getString(R.string.login_username_cannot_be_null))
            return false
        }

        if (!uiData.etUsername.value!!.matches(Regex(Reg.userName))) {
            uiData.etUsernameError.postValue(getString(R.string.login_username_cannot_be_null))
            return false
        }

        return true
    }

}