package com.skysoul.skydaily.module_accounts.activities.registers

import androidx.lifecycle.MutableLiveData

class RegisterUIData {

    var etUsername = MutableLiveData<String>()

    var etPassword = MutableLiveData<String>()

    var etPassword2 = MutableLiveData<String>()
    var etPasswordTip = MutableLiveData<String>()
    var etUsernameError = MutableLiveData<String>()

    var etPasswordError = MutableLiveData<String>()
    var etPasswordError2 = MutableLiveData<String>()
}
