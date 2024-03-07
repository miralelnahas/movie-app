package com.trianglz.core.database.remoteKeys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeyEntity(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)