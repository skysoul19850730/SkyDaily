package com.skysoul.skydaily.module_accounts.model.repository.resultbeans

import com.skysoul.skydaily.module_accounts.model.beans.User

class LoginResult(
    var user: User? = null,
    @LoginErr var errorType: Int = LoginErr.noErr,
    var leftTimes: Int = 5,
    var nextTryTime: String = ""
)
