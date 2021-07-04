package com.skysoul.skydaily.module_accounts.model.repository.local

import com.skysoul.skydaily.module_accounts.model.api.SSBaseRepository
import com.skysoul.skydaily.module_accounts.model.beans.ExtraColumn
import com.skysoul.skydaily.module_accounts.model.repository.ExtraColumnRepository
import com.skysoul.skydaily.module_accounts.rooms.daos.ExtrasDao

/**
 *@author skysoul
 *@date 2020/8/7
 *@description
 */
class ExtraColumnRepositoryLocal: ExtraColumnRepository, SSBaseRepository<ExtrasDao>(ExtrasDao::class.java) {
    override suspend fun getColumnsWithAccountID(accountId: Long): ArrayList<ExtraColumn> {
        return arrayListOf()
    }

    override suspend fun addColumns4AccountId(accountId: Long, columns: List<ExtraColumn>){

            mDao.addColumns4AccountId(accountId,columns.map {
                it.toDM()
            })
    }
}