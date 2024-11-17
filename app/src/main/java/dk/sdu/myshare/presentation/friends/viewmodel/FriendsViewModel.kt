package dk.sdu.myshare.presentation.friends.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.business.model.friendship.FriendshipRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository
import dk.sdu.myshare.business.utility.ColorGenerator
import dk.sdu.myshare.business.utility.ProfileFormatter

class FriendsViewModel(
    private val userRepository: UserRepository,
    private val friendshipRepository: FriendshipRepository,
) : ViewModel() {

    private val _friends = MutableLiveData<List<UserData>>()
    val friends: LiveData<List<UserData>> = _friends

    private val generatedUserColors: MutableMap<Int, Color> = mutableMapOf()

    private val currentUserId: Int = 1 // FiXME Hardcoded for now

    init {
        refreshFriends()
    }

    fun refreshFriends() {
        val friendsResult = friendshipRepository.fetchFriendshipById(currentUserId)

        val friends = friendsResult.map { friendship ->
            val friendId = if (friendship.userId1 == currentUserId) {
                friendship.userId2
            } else {
                friendship.userId1
            }

            userRepository.fetchUserById(friendId)
        }.filterNotNull()

        _friends.postValue(friends)
    }

    fun removeFriend(friendId: Int) {
        friendshipRepository.removeFriendshipById(currentUserId, friendId)
        refreshFriends()
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
        return currentUserId // FiXME Hardcoded for now
    }
}