package com.skysoul.skydaily.module_accounts.activities.edit

import android.app.Activity
import android.content.Intent
import com.radiance.androidbase.applibcore.activity.mvvm.RBaseMvvmActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleBindAdapterController
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleBindingAdapterWithClick
import com.radiance.androidbase.rkexs.bindNorController
import com.radiance.androidbase.rkexs.layoutLV
import com.skysoul.skydaily.manages.biometricprompt.BiometricIdentifyListener
import com.skysoul.skydaily.manages.biometricprompt.BiometricPromptManager

import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.activities.JSToolbar
import com.skysoul.skydaily.module_accounts.databinding.AcActEditBinding
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import com.skysoul.skydaily.module_accounts.model.beans.ExtraColumn
import kotlinx.android.synthetic.main.ac_act_edit.*
import showToast

/**
 *@author skysoul
 *@date 2021/6/27
 *@description
 */
class EditActivity : RBaseMvvmActivity<AcActEditBinding, EditViewModel>() {

    lateinit var cateAdapter: CateAdapter
    lateinit var extraDialog: Edit_Extra_Dialog
    lateinit var extraController: SimpleBindAdapterController<ExtraColumn>

    var accountId: Long = 0

    override fun getViewLayoutId(): Int {
        return R.layout.ac_act_edit
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        mBinding.clickEvent = View.OnClickListener {
            onClick(it)
        }
        toolbar.listerner = object : JSToolbar.JSBarClickListerner {
            override fun menuClicked(menuId: Int) {
                mViewModel.save {
                    if (it) {
                        var intent = Intent()
                        intent.putExtra("account", mViewModel.account.value)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    } else {
                        showToast(getString(R.string.edit_account_added_fail))
                    }
                }
            }

            override fun navClicked() {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

        }
        cateAdapter = CateAdapter(this, mViewModel.cates.value!!)
        spinner_cate.adapter = cateAdapter
        spinner_cate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mViewModel.account.value?.cate = mViewModel.cates.value!![p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        mViewModel.cates.observe(this) {
            cateAdapter.notifyDataSetChanged()
        }
        mViewModel.cateSelection.observe(this) {
            spinner_cate.setSelection(it)
        }

        mViewModel.account.observe(this) {
            extraController = extraLv.layoutLV(this).bindNorController(
                this,
                R.layout.ac_li_extra2,
                mViewModel.account.value!!.extraColumnList
            ) {
                onItemClick { position, v ->
                    if (v.id == R.id.btn_delete) {
                        mViewModel.deleteExtraColumn(position)
                        extraController.notifyDataSetChanged()

                    } else {
                        extraDialog.showWithExtraColumn(mViewModel.account.value!!.extraColumnList[position])
                    }
                }
            }
        }

        accountId = intent.getLongExtra("accountId", 0L)
        mViewModel.init(accountId)

        extraDialog =
            Edit_Extra_Dialog(this, object : Edit_Extra_Dialog.OnEditDialogClickListerner {
                override fun onClickOk(extra: ExtraColumn) {
                    mViewModel.addExtraColumn(extra)
                    extraController.notifyDataSetChanged()
                    extraDialog.dismiss()
                }

                override fun onClickCancel() {
                    extraDialog.dismiss()
                }

            })

    }

    private fun showCateAddDialog() {
        MaterialDialog(this).show {
            title(text = "新增分类")
            input("输入分类名称")
            negativeButton(text = "取消")
            positiveButton(text = "确定") {
                mViewModel.addCate(getInputField().text.toString())
            }
        }
    }

    private fun onClick(v: View) {
        when (v.id) {
            R.id.btn_addCate -> {
                showCateAddDialog()
            }
            R.id.btn_add -> {
                extraDialog.showWithExtraColumn(null)
            }
        }
    }

    var manager: BiometricPromptManager? = null
    var dialogPassword: MaterialDialog? = null

    private fun startBiometricPromptCheck() {
        manager = BiometricPromptManager(this)
        manager?.authenticate(object : BiometricIdentifyListener {
            override fun onSuccess() {
                dialogPassword?.dismiss()
            }

            override fun onFail() {
            }

            override fun onError(code: Int, reason: String) {
            }

        })
    }

    private fun showCheckPasswordDialog() {
        if (!mViewModel.isNeedViewPasswordDialog()) {
            return
        }
        if (dialogPassword == null) {
            dialogPassword = MaterialDialog(this).show {
                title(text = "请输入查看密码")
                input("请输入查看密码")
                negativeButton(text = "取消") {
                    manager?.cancel()
                    finish()
                }
                positiveButton(text = "确定") {
                    mViewModel.checkViewPassword(getInputField().text.toString()) {
                        if (it) {
                            dialogPassword?.dismiss()
                        }
                    }
                }
                cancelable(false)
                cancelOnTouchOutside(false)
            }
        }
        dialogPassword?.show()
        if (UserInfoManager.instance.user!!.useBio) {
            startBiometricPromptCheck()
        }
    }

    override fun onResume() {
        super.onResume()
        showPasswordViewDialog()
    }

    private fun showPasswordViewDialog(){
        if (accountId == 0L) {
            return
        }
        var now = System.currentTimeMillis()
        if (now - leavingTime >= 30 * 1000) {
            showCheckPasswordDialog()
        }
    }

    var leavingTime = 0L
    override fun onStop() {
        super.onStop()
        leavingTime = System.currentTimeMillis()
    }
}