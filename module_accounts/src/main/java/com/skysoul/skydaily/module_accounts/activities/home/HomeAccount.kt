package com.skysoul.skydaily.module_accounts.activities.home

import androidx.lifecycle.MutableLiveData
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.BindingAdapterItem
import com.radiance.androidbase.applibcore.adapter.simpleadapter2.SimpleBindingItemBean
import com.skysoul.skydaily.module_accounts.R
import com.skysoul.skydaily.module_accounts.rooms.entities.SimpleAccount

/**
 *@author skysoul
 *@date 2020/8/12
 *@description
 */
class HomeAccount(var item: SimpleAccount) : BindingAdapterItem {

    override fun getViewType(): Int {
        return R.layout.ac_li_accout
    }

    var inEdit = MutableLiveData<Boolean>(false)
    var checked = MutableLiveData<Boolean>(false)

}