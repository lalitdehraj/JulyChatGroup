package com.example.july.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.july.data.db.entity.GroupEntity

@Dao
interface GroupDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGroupEntity(group: GroupEntity)

    @Query("SELECT * FROM group_table")
    fun getGroupEntity(): LiveData<List<GroupEntity>>
}