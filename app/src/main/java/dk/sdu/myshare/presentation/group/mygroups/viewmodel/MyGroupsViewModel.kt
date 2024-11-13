package dk.sdu.myshare.presentation.group.mygroups.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dk.sdu.myshare.business.model.group.GroupData
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserRepository

class MyGroupsViewModel(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val currentUserId: Int
) {
    private val _myGroups: MutableLiveData<List<GroupData>> = MutableLiveData<List<GroupData>>()
    val myGroups: LiveData<List<GroupData>> = _myGroups

    init {
        refreshCurrentGroups()
    }

    fun refreshCurrentGroups() {
        val myGroupsResult: List<GroupData> = groupRepository.fetchGroupWhereUserIsMember(currentUserId)
        val sortedGroups = myGroupsResult.sortedBy { it.name }
        _myGroups.postValue(sortedGroups)
    }
}