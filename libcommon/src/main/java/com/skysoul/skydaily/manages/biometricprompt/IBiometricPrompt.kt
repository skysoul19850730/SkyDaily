package com.skysoul.skydaily.manages.biometricprompt

import com.skysoul.skydaily.manages.biometricprompt.BiometricIdentifyListener

/**
 *@author skysoul
 *@date 2020/9/1
 *@description
 */
interface IBiometricPrompt {

    fun authenticate(listener: BiometricIdentifyListener)

    fun isBiometricPromptEnable():Boolean
    fun cancel()
}