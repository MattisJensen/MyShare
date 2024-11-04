package dk.sdu.myshare.presentation.group.managegroupmember.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.business.model.group.GroupData
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository
import dk.sdu.myshare.business.utility.ColorGenerator
import dk.sdu.myshare.business.utility.ProfileFormatter
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.group.managegroupmember.view.ManageGroupMemberView

class ManageGroupMemberViewModel(private val userRepository: UserRepository, private val groupRepository: GroupRepository, private val selectedGroupId: Int) : ViewModel() {
    private val _addUserToGroupCandidates = MutableLiveData<Map<UserData, Boolean>>()
    val addUserToGroupCandidates: LiveData<Map<UserData, Boolean>> get() = _addUserToGroupCandidates

    private val _groupData: MutableLiveData<GroupData> = MutableLiveData<GroupData>()
    val groupData: LiveData<GroupData> = _groupData

    private val generatedUserColors: MutableMap<Int, Color> = mutableMapOf()

    init {
        refreshCurrentGroup()
    }

    fun onViewLoad() {
        refreshCurrentGroup()
        refreshAddUserToGroupCandidates()
    }

    fun refreshCurrentGroup() {
        val groupDataResult: GroupData? = groupRepository.fetchGroupDataByID(selectedGroupId)
        groupDataResult?.let {
            _groupData.postValue(it)
        }
    }

    fun refreshAddUserToGroupCandidates() {
        if (groupData.value == null) {
            return
        }

        val addUserToGroupCandidates: MutableMap<UserData, Boolean> = mutableMapOf()
        val allUsers: List<UserData> = userRepository.fetchAllUsers() ?: emptyList()

        // add each user to the map with a boolean value indicating if they are already in the group. True if they are, false if they are not.
        allUsers.forEach { user ->
            addUserToGroupCandidates[user] = groupData.value?.members?.contains(user.id) ?: false
        }

        _addUserToGroupCandidates.postValue(addUserToGroupCandidates)
    }

    fun addUserToGroup(userID: Int): Boolean {
        groupData.value?.id?.let { groupID ->
            refreshCurrentGroup()
            return groupRepository.addUserToGroup(userID, groupID)
        }
        return false
    }

    fun removeUserFromGroup(userID: Int): Boolean {
        groupData.value?.id?.let { groupID ->
            refreshCurrentGroup()
            return groupRepository.removeUserFromGroup(userID, groupID)
        }
        return false
    }

    fun getTemporaryUserColor(userID: Int): Color {
        if (generatedUserColors.containsKey(userID)) {
            return generatedUserColors[userID]!! // FIXME: Better way to handle this
        }

        var color: Color
        do {
            color = ColorGenerator.getRandomPastelColor()
        } while (generatedUserColors.containsValue(color))

        generatedUserColors[userID] = color
        return color
    }

    fun getNameInitials(name: String): String {
        return ProfileFormatter.getNameLetters(name)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserSearchView() {
    val manageGroupMemberViewModel = ViewModelFactory.getManageGroupMemberViewModel(1)
    ManageGroupMemberView(viewModel = manageGroupMemberViewModel, {})
}