package com.skysoul.skydaily.module_accounts.rooms.daos

import androidx.room.*
import com.skysoul.skydaily.module_accounts.rooms.BaseDao
import com.skysoul.skydaily.module_accounts.rooms.entities.AccountWithCateAndExtras
import com.skysoul.skydaily.module_accounts.rooms.entities.DMAccount
import com.skysoul.skydaily.module_accounts.rooms.entities.DMExtraColumn
import com.skysoul.skydaily.module_accounts.rooms.entities.SimpleAccount

@Dao
interface AccountDao: BaseDao<DMAccount> {

    @Transaction
    @Query("select id,platform,accountName,tip from account where userId = :userId and (:cateId is null or :cateId = 0 or cateId = :cateId) and (:keywords is null or accountName like :keywords or platform like :keywords)")
    fun getSimpleAccounts(userId: Long,cateId:Long?,keywords:String?):List<SimpleAccount>

    @Transaction
    @Query("select * from account where id =:accountId")
    fun getDetailsById(accountId:Long): AccountWithCateAndExtras

    @Transaction
    fun addAccount(dmAccount: DMAccount,extras:List<DMExtraColumn>?):Long{
       var id = dmAccount.id
//        if(id<=0) {
            id = insert(dmAccount)
//        }else {
//            update(dmAccount)
//        }
        extras?.run {
            insertExtras(extras)
        }
        return id
    }

    @Insert
    fun insertExtras(list: List<DMExtraColumn>):LongArray



}