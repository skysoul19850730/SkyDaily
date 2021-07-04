package com.skysoul.skydaily.module_accounts.activities.registers

import com.radiance.androidbase.applibcore.activity.mvvm.RBaseMvvmActivity
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputLayout

import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.databinding.AcActRegisterBinding
import startActivityWith

/**
 *@author skysoul
 *@date 2021/5/23
 *@description 基本注册信息页
 */
class RegisterAct : RBaseMvvmActivity<AcActRegisterBinding, RegisterViewModel>() {

    override fun getViewLayoutId(): Int {
        return R.layout.ac_act_register
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        mBinding.clickEvent = View.OnClickListener {
            onClick(it)
        }
        mViewModel.jumpType.observe(this) {
            if (it == 1) {
                gotoRegisterStep2()
            }
        }
    }

    private fun gotoRegisterStep2() {
        startActivityWith<RegisterAct2> { }
    }

    private fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> {
                mViewModel.register()
            }
        }
    }
}