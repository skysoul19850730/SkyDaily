package com.skysoul.skydaily.manages.biometricprompt

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.util.Log
import com.skysoul.skydaily.exts.isAboveApi23
import com.skysoul.skydaily.manages.biometricprompt.api23.BiometricPromptyApi23

/**
 *@author skysoul
 *@date 2020/9/1
 *@description
 */
class BiometricPromptManager {
    private fun log(msg:String){
        Log.d("sqc",msg)
    }

    lateinit var mImpl: IBiometricPrompt
    lateinit var mActivity: Activity

    constructor(activity: Activity){
        mActivity = activity
        if(isAboveApi23()) {
            mImpl = BiometricPromptyApi23(activity)
        }
    }

    fun authenticate(listener: BiometricIdentifyListener){
        if(!isBiometricPromptEnable()){
            listener.onError(-1,"不支持")
            return
        }
        mImpl?.authenticate(listener)
    }
    fun cancel(){
        mImpl?.cancel()
    }

    private fun isKeyguardSecure():Boolean{
        var keyguardManager = mActivity.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        return keyguardManager.isKeyguardSecure
    }
    fun isBiometricPromptEnable():Boolean{
        return mImpl.isBiometricPromptEnable() && isKeyguardSecure()
    }
}