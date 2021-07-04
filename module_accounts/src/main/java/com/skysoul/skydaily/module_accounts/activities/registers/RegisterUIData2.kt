package com.skysoul.skydaily.module_accounts.activities.registers

import androidx.lifecycle.MutableLiveData

/**
 *@author skysoul
 *@date 2021/6/20
 *@description
 */
class RegisterUIData2 {
    var etNickName:MutableLiveData<String> = MutableLiveData()
    var etNickNameError:MutableLiveData<String> = MutableLiveData()
    var etViewPassword:MutableLiveData<String> = MutableLiveData()
    var useLoginPassword:MutableLiveData<Boolean> = MutableLiveData(true)
    var useBio:MutableLiveData<Boolean> = MutableLiveData()
}