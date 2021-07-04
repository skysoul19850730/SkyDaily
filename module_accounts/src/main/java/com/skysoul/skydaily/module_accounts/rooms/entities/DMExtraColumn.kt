package com.skysoul.skydaily.module_accounts.rooms.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skysoul.skydaily.module_accounts.rooms.entities.DMAccount

@Entity(tableName = "extra_column",indices = [Index("accountId","extraKey",unique = true)])
data class DMExtraColumn(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ForeignKey(entity = DMAccount::class,parentColumns = ["id"],childColumns = ["accountId"],onDelete = ForeignKey.CASCADE)
        var accountId: Long = 0,

        
        var extraKey: String = "",
        var extraValue: String = ""
)