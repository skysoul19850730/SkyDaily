package com.skysoul.skydaily.module_accounts.activities.registers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.radiance.androidbase.applibcore.RBaseViewModel
import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import com.skysoul.skydaily.module_accounts.model.repository.IUserRepository
import com.skysoul.skydaily.module_accounts.model.repository.local.UserRepositoryLocal
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 *@author skysoul
 *@date 2021/6/20
 *@description
 */
class RegitsterViewModel2(app: Application) : RBaseViewModel(app) {

    var uiData = RegisterUIData2()
    var userRepository: IUserRepository = UserRepositoryLocal()
    var showNoPasswordSetTip = MutableLiveData<Int>(0)
    var showNoPasswordSetTipResult = MutableLiveData<Boolean>()

    var jumpType: MutableLiveData<Int> = MutableLiveData()

    override fun init() {
        val user = UserInfoManager.instance.user!!
        uiData.useLoginPassword.postValue(user.samePassword)
        uiData.useBio.postValue(user.useBio!!)
    }

    fun onSaveClick() {
        if (uiData.etNickName.value.isNullOrEmpty()) {
            toast(getString(R.string.register2_nickname_cannot_be_null))
            return
        }
        launchOnNet({
            val isNickExist = userRepository.isNickNameExist(uiData.etNickName.value!!)
            if (isNickExist) {
                toast(getString(R.string.register2_nickname_already_exist))
                return@launchOnNet
            }
            if (!uiData.useLoginPassword.value!! && uiData.etViewPassword.value.isNullOrEmpty() &&
                !uiData.useBio.value!!
            ) {
                if(!showViewPasswordNotSetTip()){
                    return@launchOnNet
                }
            }
            doSave()
            jumpType.postValue(1)
        })
    }

    fun onNoViewPasswordTipIgnore(ignore: Boolean) {
        if (ignore) {
            showNoPasswordSetTipResult.postValue(true)
        } else {
            showNoPasswordSetTipResult.postValue(false)
        }
    }

    private suspend fun showViewPasswordNotSetTip() = suspendCancellableCoroutine<Boolean> {

        if (!showNoPasswordSetTip.hasObservers()) {
            it.resume(true)
        } else {
            showNoPasswordSetTip.postValue(1)
            observer = Observer { res ->
                it.resume(res)
                if (observer != null) {
                    showNoPasswordSetTipResult.removeObserver(observer!!)
                }
            }
            showNoPasswordSetTipResult.observe(getLifeOwner(), observer!!)
        }

    }

    private var observer: Observer<Boolean>? = null

    private suspend fun doSave(){
        uiData.run {
            var user = UserInfoManager.instance.user!!.newCopy()
            user.nickName = etNickName.value!!
            user.samePassword = useLoginPassword.value!!
            user.useBio = useBio.value!!
            userRepository.updateUserBaseInfo(user)
            if(!useLoginPassword.value!!){
                userRepository.updateUserPassword4ViewAccount(user.userId,
                etViewPassword.value?:"","")
            }
        }
    }
}