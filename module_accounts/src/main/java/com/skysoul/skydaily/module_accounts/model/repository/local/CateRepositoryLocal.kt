package com.skysoul.skydaily.module_accounts.model.repository.local

import com.skysoul.skydaily.module_accounts.model.api.SSBaseRepository
import com.skysoul.skydaily.module_accounts.model.beans.Cate
import com.skysoul.skydaily.module_accounts.model.repository.CateRepository
import com.skysoul.skydaily.module_accounts.rooms.daos.CateDao


class CateRepositoryLocal : CateRepository, SSBaseRepository<CateDao>(CateDao::class.java) {
    override suspend fun getAllCatesByUserid(userId: Long): List<Cate> {

        return mDao.getAllCaesByUserid(userId).map {
            Cate(it)
        }

    }

    override suspend fun addCate(cate: Cate) {

        mDao.insert(cate.toDM()).run {
            if (this > 0) {
                cate.id = this
            }
        }
    }

    override suspend fun updateCate(cate: Cate) {

        mDao.update(cate.toDM())
    }

    override suspend fun getCateById(id: Long): Cate? {

        return mDao.getCateById(id)?.run {
            Cate(this)
        }

    }

    override suspend fun getCateByName(userId: Long, name: String): Cate? {

        return mDao.getCateByName(userId, name)?.run {
            Cate(this)
        }
    }

}