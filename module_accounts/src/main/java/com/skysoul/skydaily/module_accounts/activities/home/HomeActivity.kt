package com.skysoul.skydaily.module_accounts.activities.home

import android.content.DialogInterface
import android.graphics.Color
import com.radiance.androidbase.applibcore.activity.mvvm.RBaseMvvmActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import com.afollestad.materialdialogs.MaterialDialog
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleBindAdapterController
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleBindingAdapterWithClick
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleItemClickListener
import com.radiance.androidbase.libunit.util.SizeUtils
import com.radiance.androidbase.rkexs.bindNorController
import com.radiance.androidbase.rkexs.layoutLV

import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.activities.edit.EditActivity
import com.skysoul.skydaily.module_accounts.databinding.AcActHomeBinding
import com.skysoul.skydaily.module_accounts.model.beans.Account
import com.skysoul.skydaily.module_accounts.rooms.entities.SimpleAccount
import com.yanzhenjie.recyclerview.SwipeMenuItem
import kotlinx.android.synthetic.main.ac_act_home.*
import startActivityWith

/**
 *@author skysoul
 *@date 2021/6/26
 *@description
 */
class HomeActivity : RBaseMvvmActivity<AcActHomeBinding, HomeViewModel>() {

    private lateinit var mAdapter: SimpleBindingAdapterWithClick

    override fun getViewLayoutId(): Int {
        return R.layout.ac_act_home
    }

    override fun init(savedInstanceState: Bundle?) {
        mBinding.viewModel = mViewModel
        mBinding.clickEvent = View.OnClickListener {
            onClick(it)
        }
        recyclerView.setSwipeMenuCreator { _, rightMenu, _ ->
            val width = SizeUtils.dp2px(60f)
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            val tvSize = SizeUtils.dp2px(12f)
            rightMenu.addMenuItem(
                SwipeMenuItem(this).setWidth(width)
                    .setHeight(height).setTextColor(Color.BLACK)
                    .setTextSize(tvSize).setBackgroundColor(Color.GRAY)
                    .setText(getString(R.string.ac_edit))
            )
            rightMenu.addMenuItem(
                SwipeMenuItem(this).setWidth(width)
                    .setHeight(height).setTextColor(Color.RED)
                    .setTextSize(tvSize).setBackgroundColor(Color.GRAY)
                    .setText(getString(R.string.ac_delete))
            )

        }
        recyclerView.setOnItemMenuClickListener { menu, adapterPosition ->
            if (menu.position == 0) {
                onMenuEditClickOnPosition(adapterPosition)
            } else if (menu.position == 1) {
                onMenuDeleteClickOnPosition(adapterPosition)
            }
        }
        mAdapter = SimpleBindingAdapterWithClick.create(this) {
            items = mViewModel.accounts.value!!
            onItemClick { position, v ->
                if (mViewModel.inEdit.value!!) {
                    mViewModel.accounts.value!![position].checked.value =
                        !mViewModel.accounts.value!![position].checked.value!!
                } else {
                    showItemTipDialog(mViewModel.accounts.value!![position].item)
                }
            }
            onItemLongClick { position, v ->
                showUIAccountEdit(mViewModel.accounts.value!![position].item)
            }

        }
        recyclerView.layoutLV(this).adapter = mAdapter
        recyclerView.itemAnimator = DefaultItemAnimator().apply {
            addDuration = 1000
            removeDuration = 1000
        }

        toolbar.listerner = object : HomeToolbar.HomeBarClickListerner {
            override fun onSearchClick() {
                TODO("Not yet implemented")
            }

            override fun onNavigationClick() {
                onBackPressed()
            }

            override fun onEditClick() {
                mViewModel.changeEditStatus()
            }

        }
        toolbar4edit.listerner = object : HomeToolbar4Edit.HomeBar4EditClickListerner {
            override fun cancle() {
                onBackPressed()
            }

            override fun delete() {
                showDeleteTip {
                    mViewModel.removeAllChecked()
                }
            }
        }

        mViewModel.accounts.observe(this) {
            mAdapter.items = it
            mAdapter.notifyDataSetChanged()
        }
    }

    private fun onClick(v: View) {
        when (v.id) {
            R.id.fb_add -> {
                showUIAccountEdit(null)
            }
        }
    }

    private fun showUIAccountEdit(account: SimpleAccount?) {
        startActivityWith<EditActivity> {
            putExtra("accountId",account?.id?:0L)
        }
    }

    private fun onMenuEditClickOnPosition(position: Int) {
        showUIAccountEdit(mViewModel.accounts.value!![position].item)
    }

    private fun onMenuDeleteClickOnPosition(position: Int) {
        showDeleteTip {
            mViewModel.removeAtPosition(position)
        }

    }


    private fun showItemTipDialog(account: SimpleAccount) {
        var msg = account.tip ?: "没有设置密码提示"
        MaterialDialog(this).show {
            title(text = "密码提示")
            message(text = msg)
            negativeButton(text = "取消") {

            }
            positiveButton(text = "进入详情") {
                showUIAccountEdit(account)
            }
        }
    }

    private fun showDeleteTip(block:()->Unit){
        MaterialDialog(this).show {
            title(text = "删除提示")
            message(text = "删除后无法恢复，是否继续删除")
            negativeButton(text = "取消") {

            }
            positiveButton(text = "删除") {
                block.invoke()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}