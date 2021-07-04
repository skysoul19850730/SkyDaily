package com.skysoul.skydaily.manages.biometricprompt.api23

import android.app.Activity
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import com.skysoul.skydaily.exts.isAboveApi23
import com.skysoul.skydaily.manages.biometricprompt.BiometricIdentifyListener
import com.skysoul.skydaily.manages.biometricprompt.IBiometricPrompt
import java.lang.Exception

/**
 *@author skysoul
 *@date 2020/9/1
 *@description
 */
@RequiresApi(Build.VERSION_CODES.M)
class BiometricPromptyApi23 : IBiometricPrompt {

    var mActivity: Activity
    var mFingerprintManager: FingerprintManager?
    var mCancellationSignal: CancellationSignal?=null


    constructor(activity: Activity) {
        mActivity = activity
        mFingerprintManager = activity.getSystemService(FingerprintManager::class.java)
    }

    override fun authenticate(listener: BiometricIdentifyListener) {
        //先cancel之前的
        cancel()
        mCancellationSignal = CancellationSignal()

        var mFmAuthCallback = FingerprintManageCallbackImpl(listener)
        try {
            var cryptoObjectHelper = CryptoObjectHelper(mActivity)
            mFingerprintManager?.authenticate(cryptoObjectHelper.buildCryptoObject(),mCancellationSignal!!
            ,0,mFmAuthCallback,null)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    override fun cancel() {
        if(mCancellationSignal!=null && !mCancellationSignal!!.isCanceled) {
            mCancellationSignal!!.cancel()
        }
    }

    class FingerprintManageCallbackImpl(var biometricIdentifyListener: BiometricIdentifyListener?) : FingerprintManager.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)
            biometricIdentifyListener?.onError(errorCode,errString?.toString()?:"")
        }

       override fun onAuthenticationFailed() {
           super.onAuthenticationFailed()
           biometricIdentifyListener?.onFail()
       }

       override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
           super.onAuthenticationHelp(helpCode, helpString)
           biometricIdentifyListener?.onFail()
       }

       override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
           super.onAuthenticationSucceeded(result)
           biometricIdentifyListener?.onSuccess()
       }
    }

    private fun isHardwareDetected(): Boolean {
        return mFingerprintManager?.isHardwareDetected ?: false
    }
    private fun hasEnrolledFingerprints():Boolean{
        return mFingerprintManager?.hasEnrolledFingerprints()?:false
    }

    override fun isBiometricPromptEnable(): Boolean {
        return isAboveApi23() && hasEnrolledFingerprints() && isHardwareDetected()
    }
}