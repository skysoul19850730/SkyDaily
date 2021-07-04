package com.skysoul.skydaily.module_accounts.model.repository

import com.skysoul.skydaily.module_accounts.model.beans.Cate


interface CateRepository {

    suspend fun getAllCatesByUserid(userId: Long): List<Cate>
    suspend fun addCate(cate: Cate)
    suspend fun updateCate(cate: Cate)
    suspend fun getCateById(id: Long): Cate?
    suspend fun getCateByName(userId: Long, name: String): Cate?
}