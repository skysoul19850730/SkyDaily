package com.skysoul.skydaily.module_accounts.activities.login

import androidx.lifecycle.MutableLiveData

/**
 *@author skysoul
 *@date 2021/5/16
 *@description
 */
class LoginUIBean(){
    var etUsername:MutableLiveData<String> = MutableLiveData()
    var etPassword:MutableLiveData<String> = MutableLiveData()
    var etUsernameError:MutableLiveData<String> = MutableLiveData()
    var etPasswordError:MutableLiveData<String> = MutableLiveData()
}
