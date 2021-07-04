package com.skysoul.skydaily.module_accounts.model.beans

import android.os.Parcelable
import com.skysoul.skydaily.module_accounts.rooms.entities.AccountWithCateAndExtras
import com.skysoul.skydaily.module_accounts.rooms.entities.DMAccount
import com.skysoul.skydaily.module_accounts.rooms.entities.SimpleAccount
import kotlinx.android.parcel.Parcelize

/**
 * Created by Administrator on 2018/4/2.
 */
@Parcelize
class Account() : Parcelable, Cloneable {

    constructor(dmAccountWithCateAndExtras: AccountWithCateAndExtras) : this() {
        var dmAccount = dmAccountWithCateAndExtras.account!!
        this.id = dmAccount.id
        this.accountName = dmAccount.accountName
        this.bindmail = dmAccount.bindMail
        this.bindphone = dmAccount.bindPhone
        this.cate = Cate(dmAccountWithCateAndExtras.cate!!)
        this.create_time = dmAccount.createTime
        this.password = dmAccount.password
        this.platform = dmAccount.platform
        this.tip = dmAccount.tip
        this.userId = dmAccount.userId
        this.extraColumnList = arrayListOf()
        this.extraColumnList!!.addAll(
            dmAccountWithCateAndExtras.extrasColumns!!.map {
                ExtraColumn(it)
            })

    }

    fun toDM(): DMAccount {
        return DMAccount().apply {
            this.id = this@Account.id
            this.accountName = this@Account.accountName
            this.bindMail = this@Account.bindmail
            this.bindPhone = this@Account.bindphone
            this.cateId = this@Account.cate?.id ?: 1
            this.createTime = this@Account.create_time
            this.password = this@Account.password
            this.platform = this@Account.platform
            this.tip = this@Account.tip
            this.userId = this@Account.userId

        }
    }

    fun toSimple(): SimpleAccount {
        return SimpleAccount(id, platform, accountName, tip)
    }

    public override fun clone(): Account {
        var account = Account()
        account.userId = userId;
        account.id = id
        account.platform = platform
        account.password = password
        account.tip = tip
        account.bindphone = bindphone
        account.bindmail = bindmail
        account.create_time = create_time
        account.accountName = accountName
        account.cate = cate
        account.extraColumnList = extraColumnList
        return account
    }

    override fun equals(other: Any?): Boolean {
        if (other is Account) {
            return id == other.id
        }
        return super.equals(other)
    }

    var id: Long = 0
    var platform: String = ""
    var password: String = ""
    var tip: String = ""
    var bindphone: String = ""
    var bindmail: String = ""
    var create_time: String = ""
    var accountName: String = ""
    var userId: Long = 0

    var cate: Cate? = null
    var extraColumnList: ArrayList<ExtraColumn> = arrayListOf()

}