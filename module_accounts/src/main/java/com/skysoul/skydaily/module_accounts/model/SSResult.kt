package com.jscoolstar.accountremeber.model

import com.radiance.androidbase.network.IBaseResponse

/**
 * Created by luyao
 * on 2019/10/12 11:08
 */
data class SSResult<T>(val code: Int, val result: T?,val msg:String?="") : IBaseResponse<T> {

    constructor(code:Int, msg:String?=""):this(code,null,msg)
    constructor(result:T?):this(0,result,"")

    override fun code(): Int {
        return code
    }

    override fun data(): T? {
        return result
    }

    override fun isSuccess(): Boolean {
        return code == 0
    }

    override fun msg(): String? {
        return msg
    }
}