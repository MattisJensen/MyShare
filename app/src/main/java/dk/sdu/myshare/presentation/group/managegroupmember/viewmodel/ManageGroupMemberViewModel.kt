package dk.sdu.myshare.presentation.group.managegroupmember.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.business.model.group.GroupData
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository
import dk.sdu.myshare.business.utility.ColorGenerator
import dk.sdu.myshare.business.utility.ProfileFormatter

class ManageGroupMemberViewModel(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val openGroupId: Int
) : ViewModel() {
    private val _currentGroup: MutableLiveData<GroupData> = MutableLiveData<GroupData>()
    val currentGroup: LiveData<GroupData> = _currentGroup

    private val _groupMembers: MutableLiveData<List<UserData>> = MutableLiveData<List<UserData>>()
    val groupMembers: LiveData<List<UserData>> = _groupMembers

    private val _addUserToGroupCandidates = MutableLiveData<List<UserData>>()
    val addUserToGroupCandidates: LiveData<List<UserData>> get() = _addUserToGroupCandidates

    private val _filteredMembers = MutableLiveData<List<UserData>>()
    val filteredMembers: LiveData<List<UserData>> get() = _filteredMembers

    private val _filteredCandidates = MutableLiveData<List<UserData>>()
    val filteredCandidates: LiveData<List<UserData>> get() = _filteredCandidates

    private val generatedUserColors: MutableMap<Int, Color> = mutableMapOf()

    init {
        observeGroupData()
        refreshCurrentGroup()
    }

    private fun observeGroupData() {
        currentGroup.observeForever {
            refreshCurrentGroupMembers()
            refreshAddUserToGroupCandidates()
        }
    }

    fun refreshCurrentGroup() {
        val groupDataResult: GroupData? = groupRepository.fetchGroupDataByID(openGroupId)
        groupDataResult?.let {
            _currentGroup.postValue(it)
        }
    }

    fun refreshCurrentGroupMembers() {
        if (currentGroup.value == null) {
            return
        }

        val currentMembers: MutableList<UserData> = mutableListOf()

        currentGroup.value?.members?.forEach { memberID ->
            val userDataResult: UserData? = userRepository.fetchUserById(memberID)
            userDataResult?.let {
                currentMembers.add(it)
            }
        }

        currentMembers.sortBy { it.name }
        _groupMembers.postValue(currentMembers)
        _filteredMembers.postValue(currentMembers)

    }

    fun refreshAddUserToGroupCandidates() {
        if (currentGroup.value == null) {
            return
        }

        val addUserToGroupCandidates: MutableList<UserData> = mutableListOf()
        val allUsers: List<UserData> = userRepository.fetchAllUsers() ?: emptyList()

        allUsers.forEach { user ->
            if (currentGroup.value?.members?.contains(user.id) == false) {
                addUserToGroupCandidates.add(user)
            }
        }

        addUserToGroupCandidates.sortBy { it.name }
        _addUserToGroupCandidates.postValue(addUserToGroupCandidates)
        _filteredCandidates.postValue(addUserToGroupCandidates)
    }

    fun filterUsers(query: String) {
        val filteredMembers = groupMembers.value?.filter { it.name.contains(query, ignoreCase = true) } ?: emptyList()
        val filteredCandidates = addUserToGroupCandidates.value?.filter { it.name.contains(query, ignoreCase = true) } ?: emptyList()

        _filteredMembers.postValue(filteredMembers)
        _filteredCandidates.postValue(filteredCandidates)
    }

    fun addUserToGroup(userId: Int): Boolean {
        currentGroup.value?.id?.let { groupID ->
            refreshCurrentGroup()
            return groupRepository.addUserToGroup(userId, groupID)
        }
        return false
    }

    fun removeUserFromGroup(userId: Int): Boolean {
        currentGroup.value?.id?.let { groupID ->
            refreshCurrentGroup()
            return groupRepository.removeUserFromGroup(userId, groupID)
        }
        return false
    }

    fun getTemporaryUserColor(userId: Int): Color {
        if (generatedUserColors.containsKey(userId)) {
            return generatedUserColors[userId]!! // FIXME: Better way to handle this
        }

        var color: Color
        do {
            color = ColorGenerator.getRandomPastelColor()
        } while (generatedUserColors.containsValue(color))

        generatedUserColors[userId] = color
        return color
    }

    fun getNameInitials(name: String): String {
        return ProfileFormatter.getNameLetters(name)
    }

    fun getCurrentUserId(): Int {
        return 1 //FIXME: Hardcoded user id
    }
}