package com.skysoul.skydaily.module_accounts.model.api

import java.io.IOException
import com.jscoolstar.accountremeber.model.SSResult
import com.radiance.androidbase.network.BaseRepository
import com.radiance.androidbase.network.IBaseResponse
import com.radiance.androidbase.network.ResponseThrowable
import com.skysoul.skydaily.module_accounts.rooms.BaseDao
import com.skysoul.skydaily.module_accounts.rooms.DBManager
import kotlinx.coroutines.coroutineScope

/**
 * Created by luyao
 * on 2019/4/10 9:41
 */
open class SSBaseRepository<M : BaseDao<*>> : BaseRepository {

    protected val errorMessage = "操作出现异常"
    protected var mDao: M

    constructor(clazz: Class<*>) {
        val accountDatabase = DBManager.instance.mDataBase
//        var method = AccountDatabase.javaClass.getDeclaredMethod("getDaoWithClass",Class::class.java)
//        mDao = method.invoke(accountDatabase,M::) as M
        mDao = accountDatabase.getDaoWithClass(clazz) as M
    }

    suspend fun <T, R> dbCall(block: suspend () -> R?, convert: ((r: R) -> T?)?): T? {

        return netCall {
            val r: R? = block()
            if (r == null) {
                SSResult(1)
            } else {
                if(convert == null){
                    SSResult(r as T)
                }else {
                    val t = convert(r)
                    SSResult(t)
                }
            }
        }

    }

}