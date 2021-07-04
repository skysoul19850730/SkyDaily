package com.skysoul.skydaily.module_accounts.model.repository

import com.skysoul.skydaily.module_accounts.model.beans.ExtraColumn


interface ExtraColumnRepository {


    /**
     * 根据账号id，获取所有该账号的额外属性
     */
   suspend fun getColumnsWithAccountID(accountId: Long):ArrayList<ExtraColumn>
//    /**
//     * 删除某一个属性
//     */
//    fun deleteColumnWithId(columnId: Int): Boolean
//    /**
//     * 删除账号下的所有额外属性
//     */
//    suspend fun deleteColumns4AccoutnId(accountId: Int):SSResult<Boolean>
//    /**
//     * 为账号增加或修改一个属性
//     */
//    fun addColumn4AccountId(accountId: Int, column: ExtraColumn): Boolean

    /**
     * 为账号增加或修改一组属性,传null或size==0的cloumns就代表删除原来的
     */
    suspend fun addColumns4AccountId(accountId: Long, columns: List<ExtraColumn>)
}