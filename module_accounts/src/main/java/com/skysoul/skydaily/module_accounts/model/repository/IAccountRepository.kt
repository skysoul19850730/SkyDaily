package com.skysoul.skydaily.module_accounts.model.repository

import com.skysoul.skydaily.module_accounts.model.beans.Account
import com.skysoul.skydaily.module_accounts.rooms.entities.AccountWithCateAndExtras
import com.skysoul.skydaily.module_accounts.rooms.entities.SimpleAccount

interface IAccountRepository {

    suspend fun getAccountDetailWith(accountId:Long):AccountWithCateAndExtras
    suspend fun getAccountListAll(userId:Long,cateId:Long?=null,key: String?=null):List<SimpleAccount>
    suspend fun saveOrAddAccount(account: Account)
    suspend fun deleteAccount(account: Account)
    suspend fun deleteAccountsByIds(vararg ids:Long)
//    suspend fun deleteAccountsByIds(ids:List<Long>):Boolean
}