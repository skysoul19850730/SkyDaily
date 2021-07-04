package com.skysoul.skydaily.module_accounts.activities.edit

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.radiance.androidbase.applibcore.RBaseViewModel
import com.skysoul.skydaily.exts.SimpleBoolean
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import com.skysoul.skydaily.module_accounts.model.beans.Account
import com.skysoul.skydaily.module_accounts.model.beans.Cate
import com.skysoul.skydaily.module_accounts.model.beans.ExtraColumn
import com.skysoul.skydaily.module_accounts.model.repository.local.AccountRepositoryLocal
import com.skysoul.skydaily.module_accounts.model.repository.local.CateRepositoryLocal
import com.skysoul.skydaily.module_accounts.model.repository.local.UserRepositoryLocal

/**
 *@author skysoul
 *@date 2021/6/27
 *@description
 */
class EditViewModel(app: Application) : RBaseViewModel(app) {

    var accountRepositoryLocal = AccountRepositoryLocal()
    var cateRepositoryLocal = CateRepositoryLocal()
    var account = MutableLiveData<Account>()
    var cates = MutableLiveData<ArrayList<Cate>>(arrayListOf())
    var cateSelection = MutableLiveData<Int>()
    var userId = UserInfoManager.instance.user!!.userId

    override fun init() {
    }

    fun init(accountId: Long) {

        launchOnNet({

            cates.value!!.addAll(cateRepositoryLocal.getAllCatesByUserid(userId))
            cates.postValue(cates.value)
            if (accountId == 0L) {
                account.postValue(Account().apply {
                    userId = this@EditViewModel.userId
                    cate = cates.value!![0]
                    postCateSelection(cate!!)
                })
            } else {
                account.postValue(Account(accountRepositoryLocal.getAccountDetailWith(accountId)).apply {
                    postCateSelection(cate!!)
                })
            }


        })
    }

    fun postCateSelection(cateTo: Cate) {
        cates.value?.run {
            for ((index, cate) in this.withIndex()) {
                if (cate.id == cateTo.id) {
                    cateSelection.postValue(index)
                    break
                }
            }
        }

    }

    fun isNeedViewPasswordDialog(): Boolean {
        return UserInfoManager.instance.user!!.run {
            useBio || viewPasswordSet
        }
    }

    fun save(callback: SimpleBoolean) {
        launchOnNet({
            accountRepositoryLocal.saveOrAddAccount(account.value!!)
            launchOnUI {
                callback.invoke(true)
            }

        }, {
            launchOnUI {
                callback.invoke(false)
            }

        }, {

        })
    }

    fun addCate(cateName: String) {
        launchOnNet({
            val cate = Cate()
            cate.userId = userId
            cate.cateName = cateName
            cateRepositoryLocal.addCate(cate)
            cates.value!!.add(cate)
            account.value!!.cate = cate
            cates.postValue(cates.value)
            postCateSelection(cate)
        })
    }

    fun addExtraColumn(extra: ExtraColumn) {
        account.value!!.extraColumnList.add(extra)
    }

    fun deleteExtraColumn(position: Int) {
        account.value!!.extraColumnList.removeAt(position)
    }

    fun checkViewPassword(password: String, callback: SimpleBoolean) {
        launchOnNet({
            val result = UserRepositoryLocal().checkPasswordWithType(
                1,
                UserInfoManager.instance.getUserName(),
                password
            )
            if (result.user != null) {
                callback.invoke(true)
                return@launchOnNet
            }
            callback.invoke(false)
        })
    }
}