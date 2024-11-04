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
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.managegroupmember.view.UserSearchView

class ManageGroupMemberViewModel(private val userRepository: UserRepository, private val groupRepository: GroupRepository) : ViewModel() {
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
        // FIXME: Hardcoded group ID instead of using the current opened group
        val groupDataResult: GroupData? = groupRepository.fetchGroupDataByID(1)
        groupDataResult?.let {
            _groupData.postValue(it)
        }
        refreshAddUserToGroupCandidates() // FIXME: Only for preview not for production
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


    /**
     * If the input name consists of only one word, the first two letters of the word are returned.
     *
     * @param name The full name to extract the letters from.
     * @return The first letter of a names first and last name.
     */
    fun getNameLetters(name: String): String {
        val words = name.split(" ")
        return if (words.size == 1) {
            words[0].take(2).uppercase()
        } else {
            "${words[0][0]}${words[words.size - 1][0]}".uppercase()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserSearchView() {
    val manageGroupMemberViewModel = ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository)
    UserSearchView(viewModel = manageGroupMemberViewModel, {})
}