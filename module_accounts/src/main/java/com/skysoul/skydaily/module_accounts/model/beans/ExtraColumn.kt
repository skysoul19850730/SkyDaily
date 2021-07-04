package com.skysoul.skydaily.module_accounts.model.beans
import android.os.Parcel
import android.os.Parcelable
import com.skysoul.skydaily.module_accounts.rooms.entities.DMExtraColumn
import kotlinx.android.parcel.Parcelize

/**
 * Created by Administrator on 2018/4/2.
 */
@Parcelize
class ExtraColumn() : Parcelable {
    var id: Long = 0
    var aId: Long = 0
    var key: String= ""
    var value: String= ""

    constructor(dmExtraColumn: DMExtraColumn):this(){
        id = dmExtraColumn.id
        aId = dmExtraColumn.accountId
        key = dmExtraColumn.extraKey
        value = dmExtraColumn.extraValue
    }

    fun toDM():DMExtraColumn{
        return DMExtraColumn(id,aId,key,value)
    }

}