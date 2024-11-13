package dk.sdu.myshare.presentation.group.addtogroup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dk.sdu.myshare.business.model.group.GroupData
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository

class AddUserToGroupViewModel(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val currentUserId: Int,
    private val otherUserId: Int
) {
    private val _commonGroups: MutableLiveData<List<GroupData>> = MutableLiveData<List<GroupData>>()
    val commonGroups: LiveData<List<GroupData>> = _commonGroups

    private val _groupCandidates: MutableLiveData<List<GroupData>> = MutableLiveData<List<GroupData>>()
    val groupCandidates: LiveData<List<GroupData>> = _groupCandidates

    private var otherUserName: String = ""

    init {
        refreshGroups()
        refreshOtherUser()
    }

    fun refreshGroups() {
        val groupsResult: List<GroupData> = groupRepository.fetchGroupWhereUserIsMember(currentUserId)
        val commonGroupsResult = groupsResult.filter { groupData -> groupData.members.contains(otherUserId) }

        val sortedGroups = groupsResult.sortedBy { it.name }
        val sortedCommon = commonGroupsResult.sortedBy { it.name }

        _groupCandidates.postValue(sortedGroups)
        _commonGroups.postValue(sortedCommon)
    }

    fun refreshOtherUser() {
        val otherUserResult: UserData? = userRepository.fetchUserById(otherUserId)
        otherUserResult?.let {
            otherUserName = it.name
        }
    }

    fun addUserToGroup(groupId: Int): Boolean {
        if (groupRepository.addUserToGroup(otherUserId, groupId)) {
            refreshGroups()
            return true
        }
        return false
    }

    fun removeUserFromGroup(groupId: Int): Boolean {
        if (groupRepository.removeUserFromGroup(otherUserId, groupId)) {
            refreshGroups()
            return true
        }
        return false
    }

    fun getOtherUserName(): String {
        if (otherUserName.isEmpty()) {
            return "to"
        } else {
            return "$otherUserName to"
        }
    }
}