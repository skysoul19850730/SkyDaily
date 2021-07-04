package com.skysoul.skydaily.exts

import com.skysoul.skydaily.configs.RegConfig
import java.util.regex.Pattern

/**
 *@author skysoul
 *@date 2021/5/23
 *@description
 */
fun String.isUserName(): Boolean {
    val reg_username = RegConfig.reg_username
    return isAllPatternWith(reg_username)
}

fun String.isPassword():Boolean{
    return isAllPatternWith(RegConfig.reg_password)
}



fun String.isAllPatternWith(reg:String):Boolean{
    val pattern = Pattern.compile(reg)
    return pattern.matcher(this).find()
}

typealias SimpleBoolean = (result:Boolean)->Unit
typealias SimpleT<T> = (result:T?)->Unit