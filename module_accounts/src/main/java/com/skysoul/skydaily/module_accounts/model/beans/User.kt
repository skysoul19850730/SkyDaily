package com.skysoul.skydaily.module_accounts.model.beans

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.skysoul.skydaily.module_accounts.rooms.entities.DMUser

class User  {

    fun newCopy(): User {
        return User().apply {
            userId = this@User.userId
            userName = this@User.userName
            nickName = this@User.nickName
            passwordTip = this@User.passwordTip
            passwordViewTip = this@User.passwordViewTip
            samePassword = this@User.samePassword
            viewPasswordSet = this@User.viewPasswordSet
            useBio = this@User.useBio
        }
    }

    var userId: Long = 0

    var userName:String = ""

    var nickName: String = ""
    var passwordTip: String = ""
    var passwordViewTip: String = ""

    var samePassword: Boolean = false

    var viewPasswordSet: Boolean  = false

    var useBio : Boolean = false

    constructor()

    constructor(dmUser: DMUser) {
        userId = dmUser.id
        userName = dmUser.userName
        nickName = dmUser.nickName
        passwordTip = dmUser.passwordTip
        passwordViewTip = dmUser.passwordViewTip
        samePassword = dmUser.isSamePassword
        useBio = dmUser.useBiometricPrompt
        viewPasswordSet = samePassword || !dmUser.passwordView.isNullOrEmpty() || useBio
    }

}