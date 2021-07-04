package com.skysoul.skydaily.module_accounts.activities.login

import com.radiance.androidbase.applibcore.activity.mvvm.RBaseMvvmActivity
import android.os.Bundle
import android.view.View

import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.activities.home.HomeActivity
import com.skysoul.skydaily.module_accounts.activities.registers.RegisterAct
import com.skysoul.skydaily.module_accounts.activities.registers.RegisterAct2
import com.skysoul.skydaily.module_accounts.databinding.AcActLoginBinding
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import startActivityWith

/**
 *@author skysoul
 *@date 2021/5/16
 *@description
 */
class LoginAct : RBaseMvvmActivity<AcActLoginBinding, LoginViewModel>() {

    override fun getViewLayoutId(): Int {
        return R.layout.ac_act_login
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        mBinding.clickEvent = View.OnClickListener {
            onClick(it)
        }
        mViewModel.jumpType.observe(this) {
            if (it == 1) {
                gotoHome()
            }
        }
        mViewModel.getLastUser()
    }

    private fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                mViewModel.doLogin()
            }
            R.id.tv_register -> {
                gotoRegister()
            }
        }
    }

    private fun gotoRegister() {
        startActivityWith<RegisterAct> { }
    }

    private fun gotoRegister2() {
        startActivityWith<RegisterAct2> { }
    }

    private fun gotoHome() {

        val user = UserInfoManager.instance.user ?: return

        if (user.nickName.isNullOrEmpty()) {
            gotoRegister2()
            return
        }

        startActivityWith<HomeActivity> {  }

    }
}