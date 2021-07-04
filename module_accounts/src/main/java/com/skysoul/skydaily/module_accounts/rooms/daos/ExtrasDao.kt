package com.skysoul.skydaily.module_accounts.rooms.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.skysoul.skydaily.module_accounts.rooms.BaseDao
import com.skysoul.skydaily.module_accounts.rooms.entities.DMExtraColumn

@Dao
interface ExtrasDao: BaseDao<DMExtraColumn> {

    @Query("select * from extra_column where accountId = :accountId")
    fun getExtrasByAccountId(accountId:Long):List<DMExtraColumn>

    @Query("delete from extra_column where accountId = :accountId")
    fun deleteAllFromAccount(accountId: Long)

    @Transaction
    fun addColumns4AccountId(accountId: Long,list:List<DMExtraColumn>){
        deleteAllFromAccount(accountId)
        insert(list)
    }
}