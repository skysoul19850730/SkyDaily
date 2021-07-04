package com.skysoul.skydaily.module_accounts.rooms

import androidx.room.*

interface BaseDao<T> {

    @Insert
    fun insert(t: T):Long

    @Insert
    fun insert(list: List<T>):LongArray

    @Delete
    fun delete(t: T)

    @Delete
    fun delete(list: List<T>)

    @Update
    fun update(t: T)

    @Update
    fun update(list: List<T>)

}