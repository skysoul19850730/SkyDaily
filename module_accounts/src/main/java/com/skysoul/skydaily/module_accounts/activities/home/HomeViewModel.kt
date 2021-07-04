package com.skysoul.skydaily.module_accounts.activities.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.radiance.androidbase.applibcore.RBaseViewModel
import com.skysoul.skydaily.module_accounts.manager.UserInfoManager
import com.skysoul.skydaily.module_accounts.model.beans.Account
import com.skysoul.skydaily.module_accounts.model.repository.IAccountRepository
import com.skysoul.skydaily.module_accounts.model.repository.local.AccountRepositoryLocal

/**
 *@author skysoul
 *@date 2021/6/26
 *@description
 */
class HomeViewModel(app: Application) : RBaseViewModel(app) {

    var accountRepository:IAccountRepository = AccountRepositoryLocal()
    var accounts= MutableLiveData<ArrayList<HomeAccount>>(arrayListOf())
    var inEdit = MutableLiveData<Boolean>(false)

    var notifyInsert: MutableLiveData<Int> = MutableLiveData()
    var notifyDelete: MutableLiveData<Int> = MutableLiveData()

    override fun init() {
        launchOnNet({
            val result = accountRepository.getAccountListAll(UserInfoManager.instance.getUserId())
                .map {
                    HomeAccount(it)
                }
            accounts.value!!.addAll(result)
            accounts.postValue(accounts.value)
        })
    }

    fun changeEditStatus(){
        inEdit.postValue(!inEdit.value!!)
    }

    fun removeAtPosition(position:Int){
        launchOnNet({
            accountRepository.deleteAccountsByIds(accounts.value!![position].item.id)
            accounts.value!!.removeAt(position)
            notifyDelete.postValue(position)
        })
    }

    fun removeAllChecked(){
        launchOnNet({
            val needDeleted = accounts.value!!.filter {
                it.checked.value!!
            }
            var needDeleteIds = needDeleted.map {
                it.item.id
            }
            accountRepository.deleteAccountsByIds(*needDeleteIds.toLongArray())
            accounts.value!!.removeAll(needDeleted)
            accounts.postValue(accounts.value)
        })
    }
}