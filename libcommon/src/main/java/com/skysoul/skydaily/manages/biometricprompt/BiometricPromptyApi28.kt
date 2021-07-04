//package com.jscoolstar.jscoolstarlibrary.manages.biometricprompt
//
//import android.app.Activity
//import android.hardware.biometrics.BiometricPrompt
//import android.os.CancellationSignal
//import java.security.Signature
//
///**
// *@author skysoul
// *@date 2020/9/2
// *@description
// */
//class BiometricPromptyApi28:IBiometricPrompt {
//
//    private var KEY_NAME=""
//    private var mActivity:Activity
//    private var mBiometricPrompt:BiometricPrompt
//   private var biometricIdentifyListener: BiometricIdentifyListener?=  null
//   private var mCancellationSignal: CancellationSignal?=null
//    private var mSignature:Signature?=null
//    private var mToBeSignedMessage:String?=null
//
//    constructor(activity: Activity){
//        mActivity = activity
//    }
//
//
//    override fun authenticate(listener: BiometricIdentifyListener) {
//        TODO("Not yet implemented")
//    }
//}