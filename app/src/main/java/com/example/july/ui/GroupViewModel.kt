package com.example.july.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.july.data.db.entity.GroupEntity
import com.example.july.domain.repositories.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val groupRepository: GroupRepository
) : ViewModel() {
    val groups: LiveData<List<GroupEntity>> = groupRepository.fetchGroupsFromDatabase()

    fun sendGroupToDatabase(groupName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                groupRepository.insertGroupIntoDatabse(GroupEntity(groupName))
            }
        }
    }
}