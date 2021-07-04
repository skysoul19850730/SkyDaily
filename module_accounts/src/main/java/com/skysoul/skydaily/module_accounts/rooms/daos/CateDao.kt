package com.skysoul.skydaily.module_accounts.rooms.daos

import androidx.room.Dao
import androidx.room.Query
import com.skysoul.skydaily.module_accounts.rooms.BaseDao
import com.skysoul.skydaily.module_accounts.rooms.entities.DMCate

@Dao
interface CateDao : BaseDao<DMCate> {

    @Query("select * from cate where userId = :userId")
    fun getAllCaesByUserid(userId: Long): List<DMCate>

    @Query("select * from cate where id = :id")
    fun getCateById(id: Long): DMCate?

    @Query("select * from cate where userId = :userId and cateName = :name")
    fun getCateByName(userId: Long, name: String): DMCate?

}