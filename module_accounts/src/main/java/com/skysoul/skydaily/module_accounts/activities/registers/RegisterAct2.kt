package com.skysoul.skydaily.module_accounts.activities.registers

import com.radiance.androidbase.applibcore.activity.mvvm.RBaseMvvmActivity
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input

import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.activities.home.HomeActivity
import com.skysoul.skydaily.module_accounts.databinding.AcActRegister2Binding
import kotlinx.android.synthetic.main.ac_act_register2.*
import startActivityWith

/**
 *@author skysoul
 *@date 2021/6/20
 *@description
 */
class RegisterAct2 : RBaseMvvmActivity<AcActRegister2Binding, RegitsterViewModel2>() {

    override fun getViewLayoutId(): Int {
        return R.layout.ac_act_register2
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        mBinding.clickEvent = View.OnClickListener {
            onClick(it)
        }
        mViewModel.showNoPasswordSetTip.observe(this) {
            if (it > 0) {
                showDialog4NoViewPasswordSet()
            }
        }
        mViewModel.jumpType.observe(this) {
            if (it > 0) {
                goHome()
            }
        }
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun showDialog4NoViewPasswordSet() {
        MaterialDialog(this).show {
            title(R.string.register2_dialog_nopassword_title)
            message(R.string.register2_dialog_nopassword_content)
            negativeButton(R.string.dialog_ok) {
                mViewModel.onNoViewPasswordTipIgnore(true)
            }
            positiveButton(R.string.dialog_cancel) {
                mViewModel.onNoViewPasswordTipIgnore(false)
            }
            cancelable(false)
        }
    }

    private fun goHome() {
        startActivityWith<HomeActivity> { }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MaterialDialog(this).show {
            title(R.string.register2_dialog_nopassword_title)
            message(R.string.register2_dialog_nopassword_content)
            negativeButton(R.string.dialog_ok) {
                goHome()
            }
            positiveButton(R.string.dialog_cancel) {
            }
            cancelable(false)
        }
    }

    private fun onClick(v: View) {
        when (v.id) {
            R.id.tv_ok -> mViewModel.onSaveClick()
        }
    }
}