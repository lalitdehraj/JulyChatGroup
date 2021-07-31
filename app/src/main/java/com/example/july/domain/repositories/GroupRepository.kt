package com.example.july.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.july.data.db.entity.GroupEntity

interface GroupRepository {
    fun fetchGroupsFromDatabase(): LiveData<List<GroupEntity>>
    fun insertGroupIntoDatabse(groupEntity: GroupEntity)
}