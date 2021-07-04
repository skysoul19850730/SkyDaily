package com.skysoul.skydaily.module_accounts.model.beans

import android.os.Parcelable
import com.skysoul.skydaily.module_accounts.rooms.entities.DMCate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cate(var id: Long = 0, var cateName: String = "", var userId: Long = 0) : Parcelable {

    constructor(dmCate: DMCate) : this(dmCate.id, dmCate.cateName, dmCate.userId) {
    }

    fun toDM(): DMCate {
        return DMCate(id, cateName, userId)
    }
}