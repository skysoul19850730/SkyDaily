package com.skysoul.skydaily.module_accounts.model.repository.local

import com.jscoolstar.accountremeber.model.SSResult
import com.skysoul.skydaily.module_accounts.config.StaticConfig
import com.skysoul.skydaily.module_accounts.model.api.SSBaseRepository
import com.skysoul.skydaily.module_accounts.model.beans.Cate
import com.skysoul.skydaily.module_accounts.model.beans.User
import com.skysoul.skydaily.module_accounts.model.repository.IUserRepository
import com.skysoul.skydaily.module_accounts.model.repository.resultbeans.LoginErr
import com.skysoul.skydaily.module_accounts.model.repository.resultbeans.LoginResult
import com.skysoul.skydaily.module_accounts.model.repository.resultbeans.ViewCheck
import com.skysoul.skydaily.module_accounts.rooms.daos.UserDao
import com.skysoul.skydaily.module_accounts.rooms.entities.DMUser
import com.skysoul.skydaily.module_accounts.rooms.entities.DMUserNames
import com.skysoul.skydaily.utils.MD5
import java.lang.Exception
import java.util.*

class UserRepositoryLocal : IUserRepository, SSBaseRepository<UserDao>(UserDao::class.java) {

    override suspend fun getUserById(userId: Long): User? {
//        return dbCall({
//            mDao.getUserById(userId)
//        },{
//            User(it)
//        })
        var user = mDao.getUserById(userId)?:return null;
        return User(user)
    }


    override suspend fun getAllUserList(): List<DMUserNames> {
//        return  dbCall ({
//            mDao.getUserNameList()
//        },{
//            it
//        })?: arrayListOf()

        return mDao.getUserNameList()?: arrayListOf()
    }

    override suspend fun updateUserPassword4ViewAccount(
        userId: Long,
        password: String,
        passwordTip: String
    ) {
        mDao.getUserById(userId)?.run {
            passwordView = MD5.getMD5(password)
            passwordViewTip = passwordTip
            isSamePassword = false
            mDao.update(this)
        }
    }

    /**
     *成功后返回登陆用户信息
     */
    override suspend fun registerWith(
        userName: String,
        password: String,
        passwordTip: String
    ): User {

        if (password.isNullOrEmpty()) {
            throw Exception("密码位数错误")
        }
        if(isUserNameExist(userName)){
            throw Exception("用户已存在")
        }

        var userId = mDao.insert(DMUser().apply {
            this.userName = userName
            this.password = MD5.getMD5(password)
            this.passwordTip = passwordTip
            //默认不开启view密码
        })
        if(userId<0){
            throw Exception("注册失败")
        }
        //注册成功默认给该用户增加一个默认分类
        var cate = Cate().apply {
//            this.id = 1
            this.cateName = "default"
            this.userId = userId
        }
        CateRepositoryLocal().addCate(cate)
        return getUserById(userId)!!
    }
    override suspend fun updateUserBaseInfo(user: User) {
        mDao.getUserById(user.userId)?.run {
            nickName = user.nickName
            isSamePassword = user.samePassword
            useBiometricPrompt = user.useBio
            mDao.update(this)
        }
    }


    override suspend fun updateUserPassword4Login(
        userId: Long,
        password: String,
        passwordTip: String
    ) {
        if (password.length < 6) {
            throw Exception("密码至少6位")
        }
        mDao.getUserById(userId)?.run {
            this.password = MD5.getMD5(password)
            this.passwordTip = passwordTip
            mDao.update(this)
        }
    }

    /**
     *@type 0登陆密码，1代表view密码
     */
    override suspend fun checkPasswordWithType(
        type: Int,
        userName: String,
        password: String
    ): LoginResult {

        var dmUser = mDao.getUserByName(userName)
        return if (dmUser == null) {
            LoginResult(errorType = LoginErr.noUser)
        } else {
            //这个lastwrong在leftTimes=0的时候会返回需要等待的时间，直接返回就可以
            var (leftTimes, lastWrong) = checkRetryTimes(dmUser)
            if (leftTimes == 0) {
                LoginResult(
                    errorType = LoginErr.noTryTimes,
                    nextTryTime = lastWrong
                )
            } else {
                var password2Compare = dmUser.password
                if (type == 1 && !dmUser.isSamePassword) {
                    password2Compare = dmUser.passwordView
                }
                if (password2Compare == MD5.getMD5(password)) {
                    dmUser.resetLeftTryTimes(StaticConfig.MAX_RETRY_TIMES, "")
                    mDao.update(dmUser)
                    LoginResult(user = User(dmUser))
                } else {
                    dmUser.resetLeftTryTimes(dmUser.leftTryTimes - 1, Date().time.toString())
                    mDao.update(dmUser)
                    var (leftTimes, lastWrong) = checkRetryTimes(dmUser)
                    if (leftTimes == 0) {
                        LoginResult(
                            errorType = LoginErr.noTryTimes,
                            nextTryTime = lastWrong
                        )
                    } else
                        LoginResult(
                            errorType = LoginErr.passWordWrong,
                            leftTimes = leftTimes
                        )
                }
            }
        }
    }

    private fun checkRetryTimes(dmUser: DMUser): Pair<Int, String> {
        if (dmUser.leftTryTimes > 0) return Pair(dmUser.leftTryTimes, dmUser.lastWrongTime)
        var lastwrong = dmUser.lastWrongTime.toLong()
        var date = Date().time
        if (date - lastwrong > StaticConfig.WRONGTIME_KEEP) {//如果已经过了一天
            dmUser.leftTryTimes = StaticConfig.MAX_RETRY_TIMES
            dmUser.lastWrongTime = ""
            mDao.update(dmUser)
            return Pair(StaticConfig.MAX_RETRY_TIMES, "")
        } else {
            var waitHour = (lastwrong + StaticConfig.WRONGTIME_KEEP - date) / 1000 / 60 / 60
            return Pair(0, waitHour.toString())
        }
    }

    override suspend fun isNickNameExist(nickName: String): Boolean {
//        return dbCall({
//            mDao.getNickName(nickName)
//        },{
//            true
//        })?:false
        mDao.getNickName(nickName)?:return false
        return true
    }

    override suspend fun isUserNameExist(userName: String): Boolean {
//        return dbCall({
//            mDao.getUserByName(userName)
//        },{
//            true
//        })?:false
        mDao.getUserByName(userName)?:return false
        return true
    }
}