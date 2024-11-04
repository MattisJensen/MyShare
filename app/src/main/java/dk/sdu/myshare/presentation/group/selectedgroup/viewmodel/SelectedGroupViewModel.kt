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
import dk.sdu.myshare.presentation.group.selectedgroup.view.GroupView
import kotlin.math.pow
import kotlin.random.Random

class GroupViewModel(private val userRepository: UserRepository, private val groupRepository: GroupRepository) : ViewModel() {
    private val _userData: MutableLiveData<List<UserData>> = MutableLiveData<List<UserData>>(emptyList())
    val userData: LiveData<List<UserData>> = _userData

    private val _groupData: MutableLiveData<GroupData> = MutableLiveData<GroupData>()
    val groupData: LiveData<GroupData> = _groupData

    private val _addUserToGroupCandidates = MutableLiveData<Map<UserData, Boolean>>()
    val addUserToGroupCandidates: LiveData<Map<UserData, Boolean>> get() = _addUserToGroupCandidates

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

    fun getTemporaryUserColor(userID: Int): Color {
        if (generatedUserColors.containsKey(userID)) {
            return generatedUserColors[userID]!!
        }

        var color: Color
        do {
            color = getRandomPastelColor()
        } while (generatedUserColors.containsValue(color))

        generatedUserColors[userID] = color
        return color
    }

    fun setShowUserSearch(show: Boolean) {
        _showUserSearch.value = show
    }

    /**
     * Generates a random pastel color.
     *
     * The function generates random RGB values, calculates the luminance of the color,
     * to adjusts the RGB values if the contrast to white is not good enough.
     *
     * @return A Color object representing the generated pastel color.
     */
    fun getRandomPastelColor(): Color {
        val random = Random.Default
        var red: Int
        var green: Int
        var blue: Int
        var luminance: Double

        do {
            // Generate random RGB values for a pastel color
            red = (random.nextInt(256) + 255) / 2
            green = (random.nextInt(256) + 255) / 2
            blue = (random.nextInt(256) + 255) / 2

            // Calculate the luminance of the color
            luminance = 0.2126 * (red / 255.0).pow(2.2) +
                    0.7152 * (green / 255.0).pow(2.2) +
                    0.0722 * (blue / 255.0).pow(2.2)
        } while (luminance > 0.5) // Repeat if luminance not jas enough contrast with white

        return Color(red, green, blue)
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
    val dependencyInjectionContainer: DependencyInjectionContainer = DependencyInjectionContainer()
    val groupViewModel: GroupViewModel = dependencyInjectionContainer.groupViewModel
    GroupView(viewModel = groupViewModel)
}