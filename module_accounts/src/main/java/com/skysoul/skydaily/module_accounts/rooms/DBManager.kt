package com.skysoul.skydaily.module_accounts.rooms

import androidx.room.Room
import com.radiance.androidbase.libunit.util.Utils

/**
 *@author shenqichao
 *Created on 2021/1/18
 *@Description
 */
class DBManager private constructor(){

    private val dbName = "account_db"
    var mDataBase:AccountDatabase
    init {
        mDataBase = Room.databaseBuilder(Utils.getApp(),AccountDatabase::class.java,dbName).allowMainThreadQueries().build()
    }

    companion object {
        val instance: DBManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DBManager() }
    }
}