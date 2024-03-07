package com.trianglz.core.database.remoteKeys

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKeys: List<RemoteKeyEntity>) : Long

    @Query("SELECT * FROM remotekeyentity WHERE id = :id")
    suspend fun remoteKeysRepoId(id: Int): RemoteKeyEntity?

    @Query("DELETE FROM remotekeyentity")
    suspend fun clearRemoteKeys()


}