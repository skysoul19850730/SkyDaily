
import com.skysoul.skydaily.module_accounts.model.beans.Account
import com.skysoul.skydaily.module_accounts.model.beans.Cate
import com.skysoul.skydaily.module_accounts.model.beans.ExtraColumn
import com.skysoul.skydaily.module_accounts.model.beans.User
import com.skysoul.skydaily.module_accounts.rooms.entities.DMAccount
import com.skysoul.skydaily.module_accounts.rooms.entities.DMCate
import com.skysoul.skydaily.module_accounts.rooms.entities.DMExtraColumn
import com.skysoul.skydaily.module_accounts.rooms.entities.DMUser

fun DMExtraColumn.toExtraColumn(): ExtraColumn {
    var ex = ExtraColumn()
    ex.id = id
    ex.aId = accountId
    ex.key = extraKey
    ex.value = extraValue
    return ex
}

fun ExtraColumn.toDMExtraColumn(): DMExtraColumn {
    var dmColumn = DMExtraColumn()
    dmColumn.id = id
    dmColumn.accountId = aId
    dmColumn.extraKey = key
    dmColumn.extraValue = value
    return dmColumn
}

fun Cate.toDMCate(): DMCate {
    var dmCate = DMCate()
    dmCate.id = id
    dmCate.cateName = cateName
    dmCate.userId = userId
    return dmCate
}

fun DMCate.toCate(): Cate {
    var cate = Cate()
    cate.id = id
    cate.cateName = cateName
    cate.userId = userId
    return cate
}

fun DMAccount.toAccount(): Account {
    var account = Account()
    account.userId = userId
    account.id = id
    account.accountName = accountName
    account.bindmail = bindMail
    account.bindphone = bindPhone
    account.platform = platform
    account.tip = tip
    account.password = password
    account.create_time = createTime
    account.userId = userId
    return account
}

fun Account.toDMAccount(): DMAccount {
    var account = DMAccount()
    account.id = id
    account.accountName = accountName
    account.bindMail = bindmail
    account.bindPhone = bindphone
    account.platform = platform
    account.tip = tip
    account.password = password
    account.createTime = create_time
    account.userId = userId
    account.cateId = cate?.id?:0
    return account
}

fun User.toDMUser(): DMUser {
    var user = DMUser()
    user.id = userId
    user.nickName = nickName?:""
    user.passwordTip = passwordTip
    return user
}

fun DMUser.toUser(): User {
    var user = User()
    user.userId = id
    user.nickName = nickName
    user.passwordTip = passwordTip
    return user
}