package com.skysoul.skydaily.manages.biometricprompt

/**
 *@author skysoul
 *@date 2020/9/1
 *@description
 */
interface BiometricIdentifyListener {
    fun onSuccess()
    fun onFail()
    fun onError(code:Int,reason:String)
}