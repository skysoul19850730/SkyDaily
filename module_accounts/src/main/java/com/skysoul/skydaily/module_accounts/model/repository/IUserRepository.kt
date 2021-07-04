package com.skysoul.skydaily.module_accounts.model.repository

import com.skysoul.skydaily.module_accounts.model.beans.User
import com.skysoul.skydaily.module_accounts.model.repository.resultbeans.LoginResult
import com.skysoul.skydaily.module_accounts.model.repository.resultbeans.ViewCheck
import com.skysoul.skydaily.module_accounts.rooms.entities.DMUserNames

interface IUserRepository {

    suspend fun getUserById(userId: Long): User?
    suspend fun getAllUserList(): List<DMUserNames>
    suspend fun updateUserBaseInfo(user: User)
    suspend fun updateUserPassword4Login(userId:Long,password:String,passwordTip:String)

    suspend fun isNickNameExist(nickName:String):Boolean
    suspend fun isUserNameExist(userName:String):Boolean
    /** 更新查看账户详情密码  */
    suspend fun updateUserPassword4ViewAccount(userId:Long, password:String,passwordTip: String)


    suspend fun registerWith(userName: String, password: String, passwordTip: String): User

    suspend fun checkPasswordWithType(type: Int,userName: String,password: String):LoginResult
//    suspend fun login(userName: String,password: String):LoginResult
//    suspend fun checkViewPassword(userId: Long,viewPassword:String):ViewCheck
}