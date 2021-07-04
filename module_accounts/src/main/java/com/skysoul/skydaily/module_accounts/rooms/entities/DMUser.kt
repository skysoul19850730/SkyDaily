package com.skysoul.skydaily.module_accounts.rooms.entities

import androidx.room.*
import com.skysoul.skydaily.module_accounts.rooms.converts.MD5Convert
import com.skysoul.skydaily.utils.toMd5

@Entity(tableName = "user")
@TypeConverters(MD5Convert::class)
data class DMUser constructor(@PrimaryKey(autoGenerate = true)
                              var id: Long) {
    constructor() : this(0)
    var userName: String = ""
    var password: String = ""
        /**
         *默认赋值用户名
         */
    var nickName: String = userName
        /**
         *密码提示
         */
    var passwordTip: String = ""
        /**
         *剩余尝试次数
         */
    var leftTryTimes: Int = 5
        /**
         *记录上一次错误的时间，用以判断隔多久之后可以重新尝试
         */
    var lastWrongTime: String = ""
        /**
         *每次查看时 设置的密码
         */
    var passwordView: String = ""
        /**
         *查看时的密码提示
         */
    var passwordViewTip: String = ""
        /**
         *查看密码和登陆密码 默认不一致，即刚注册用户=未设置view密码
         */
    var isSamePassword: Boolean = false

    var useBiometricPrompt :Boolean = false

    fun updateBaseInfo(nickName2Update: String) {
        nickName = nickName2Update
    }

    fun updatePasswordLogin(password2Update: String,passwordTip: String) {
        password = password2Update.toMd5()
        this.passwordTip = passwordTip
    }

    fun updatePasswordView(password2Update: String,passwordViewTip: String) {
        passwordView = password2Update.toMd5()
        this.passwordViewTip = passwordViewTip
        isSamePassword = false//如果设置了view密码，则代码不使用登陆密码
    }
    fun updateUseLoginPassword(useLoginPassword:Boolean){
        if(useLoginPassword){
            passwordView = ""
            passwordViewTip = ""
            isSamePassword = true
        }else{
            isSamePassword = false
        }
    }
    fun resetLeftTryTimes(leftTryTimes: Int,lastWrongTime: String){
        this.leftTryTimes = leftTryTimes
        this.lastWrongTime = lastWrongTime
    }
}

/**
 *author:skysoul
 *create time:2020/4/6 11:10 PM
 *description:未登录时的用户切换列表，可以包含用户名，昵称，头像等信息
 */
data class DMUserNames(
        var nickName: String = "",
        var userName: String = ""
)


data class DMWrongUser(
        var userName: String,
        var leftTryTimes: Int,
        var lastWrongTime: String
)





