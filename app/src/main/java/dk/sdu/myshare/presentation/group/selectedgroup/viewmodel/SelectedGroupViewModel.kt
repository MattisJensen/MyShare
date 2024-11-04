package dk.sdu.myshare.presentation.group.selectedgroup.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.business.model.group.GroupData
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository
import dk.sdu.myshare.business.utility.ColorGenerator
import dk.sdu.myshare.presentation.group.selectedgroup.view.GroupView

class SelectedGroupViewModel(private val userRepository: UserRepository, private val groupRepository: GroupRepository) : ViewModel() {
    private val _userData: MutableLiveData<List<UserData>> = MutableLiveData<List<UserData>>(emptyList())
    val userData: LiveData<List<UserData>> = _userData

    private val _groupData: MutableLiveData<GroupData> = MutableLiveData<GroupData>()
    val groupData: LiveData<GroupData> = _groupData

    private val _showUserSearch = MutableLiveData(false)
    val showUserSearch: LiveData<Boolean> get() = _showUserSearch

    private val generatedUserColors: MutableMap<Int, Color> = mutableMapOf()

    init {
        refreshCurrentGroup()
        observeGroupData()
    }

    private fun observeGroupData() {
        groupData.observeForever {
            refreshCurrentGroupMembers()
        }
    }

    fun refreshCurrentGroup() {
        // FIXME: Hardcoded group ID instead of using the current opened group
        val groupDataResult: GroupData? = groupRepository.fetchGroupDataByID(1)
        groupDataResult?.let {
            _groupData.postValue(it)
        }
    }

    fun refreshCurrentGroupMembers() {
        if (groupData.value == null) {
            return
        }

        val currentUsers: MutableList<UserData> = mutableListOf()

        groupData.value?.members?.forEach { memberID ->
            val userDataResult: UserData? = userRepository.fetchUserDataByID(memberID)
            userDataResult?.let {
                currentUsers.add(it)
            }
        }

        _userData.postValue(currentUsers)
    }

    fun getTemporaryUserColor(userID: Int): Color {
        if (generatedUserColors.containsKey(userID)) {
            return generatedUserColors[userID]!!
        }

        var color: Color
        do {
            color = ColorGenerator.getRandomPastelColor()
        } while (generatedUserColors.containsValue(color))

        generatedUserColors[userID] = color
        return color
    }

    fun setShowUserSearch(show: Boolean) {
        _showUserSearch.value = show
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
fun PreviewGroupView() {
    val selectedGroupViewModel: SelectedGroupViewModel = DependencyInjectionContainer.selectedGroupViewModel
    GroupView(viewModel = selectedGroupViewModel)
}