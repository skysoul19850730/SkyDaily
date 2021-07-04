package com.skysoul.skydaily.module_accounts.model.repository.resultbeans

import com.skysoul.skydaily.module_accounts.model.beans.User

class ViewCheck(
    var leftTimes: Int = 5,
    var nextTryTime: String = "",
    var isSuc :Boolean = false
)
