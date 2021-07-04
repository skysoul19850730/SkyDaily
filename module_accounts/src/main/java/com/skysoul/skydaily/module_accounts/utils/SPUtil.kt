package com.skysoul.skydaily.module_accounts.utils

import com.radiance.androidbase.libunit.util.SPUtils

/**
 *@author skysoul
 *@date 2021/5/16
 *@description
 */
object SPUtil {
    val instance: SPUtils by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { SPUtils.getInstance() }

    const val LAST_USER_ID = "lastUserId"
    const val LAST_USER_NAME="lastUserName"
}