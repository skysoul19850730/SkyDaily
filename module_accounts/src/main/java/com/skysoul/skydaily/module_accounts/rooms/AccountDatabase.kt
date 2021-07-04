package com.skysoul.skydaily.module_accounts.rooms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.skysoul.skydaily.module_accounts.rooms.daos.*
import com.skysoul.skydaily.module_accounts.rooms.entities.*

@Database(version = 1,exportSchema = false,
        entities = [DMAccount::class, DMCate::class, DMUser::class, DMExtraColumn::class]
)
abstract class AccountDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cateDao(): CateDao
    abstract fun accountDao(): AccountDao
    abstract fun extrasDao(): ExtrasDao


    fun <T> getDaoWithClass(clazz:Class<T>):T{
        return when(clazz){
            UserDao::class.java -> userDao()
            CateDao::class.java -> cateDao()
            AccountDao::class.java ->accountDao()
            ExtrasDao::class.java -> extrasDao()
            else -> null
        } as T
    }

    companion object{
        private var INSTANCE: AccountDatabase?=null
        var lock = Any()
        fun getInstance(context: Context): AccountDatabase {
            if(INSTANCE ==null){
                synchronized(lock){
                    if(INSTANCE ==null){
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AccountDatabase::class.java,"account.db")
                                .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}