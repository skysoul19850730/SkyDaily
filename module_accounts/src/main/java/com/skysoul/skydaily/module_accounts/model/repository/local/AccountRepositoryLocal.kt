package com.skysoul.skydaily.module_accounts.model.repository.local

import com.skysoul.skydaily.module_accounts.model.api.SSBaseRepository
import com.skysoul.skydaily.module_accounts.model.beans.Account
import com.skysoul.skydaily.module_accounts.model.repository.IAccountRepository
import com.skysoul.skydaily.module_accounts.rooms.daos.AccountDao
import com.skysoul.skydaily.module_accounts.rooms.entities.AccountWithCateAndExtras
import com.skysoul.skydaily.module_accounts.rooms.entities.DMAccount
import com.skysoul.skydaily.module_accounts.rooms.entities.SimpleAccount

/**
 *@author skysoul
 *@date 2020/8/10
 *@description
 */
class AccountRepositoryLocal : IAccountRepository,
    SSBaseRepository<AccountDao>(AccountDao::class.java) {
    override suspend fun getAccountDetailWith(accountId: Long): AccountWithCateAndExtras {
        return mDao.getDetailsById(accountId)
    }

    override suspend fun getAccountListAll(
        userId: Long,
        cateId: Long?,
        key: String?
    ): List<SimpleAccount> {
        var keyNew = key
        if (!key.isNullOrEmpty()) {
            keyNew = "%$key%"
        }
        return mDao.getSimpleAccounts(userId, cateId, keyNew)
    }

    override suspend fun saveOrAddAccount(account: Account) {
        var resultId = mDao.addAccount(account.toDM(), account.extraColumnList?.map {
            it.toDM()
        })
        account.id = resultId
    }

    override suspend fun deleteAccount(account: Account) {
        mDao.delete(account.toDM())
    }

    override suspend fun deleteAccountsByIds(vararg ids: Long) {
        mDao.delete(ids.map {
            DMAccount(it)
        })
    }

}