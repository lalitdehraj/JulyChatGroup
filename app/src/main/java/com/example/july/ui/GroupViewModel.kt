package com.example.july.ui

import androidx.lifecycle.*
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
    private val _groups: LiveData<List<GroupEntity>> = groupRepository.fetchGroupsFromDatabase()
    val groups:LiveData<List<GroupEntity>>
    get() = _groups

    fun sendGroupToDatabase(groupName: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val groupEntity = GroupEntity(groupName)
                groupRepository.insertGroupIntoDatabse(groupEntity)
            }
        }
    }



}