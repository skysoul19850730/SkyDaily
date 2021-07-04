package com.skysoul.skydaily.exts

import android.os.Build

/**
 *@author skysoul
 *@date 2020/9/1
 *@description
 */

fun Any.isAboveApi(api:Int):Boolean{
    return Build.VERSION.SDK_INT>=api
}
fun Any.isAboveApi28() =  isAboveApi(Build.VERSION_CODES.P)
fun Any.isAboveApi23() =  isAboveApi(Build.VERSION_CODES.M)
