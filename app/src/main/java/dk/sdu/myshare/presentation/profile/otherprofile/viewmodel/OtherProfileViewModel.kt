package dk.sdu.myshare.presentation.profile.otherprofile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.business.model.friendship.FriendshipRepository
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository

class OtherProfileViewModel(
    private val userRepository: UserRepository,
    private val groupRepository: GroupRepository,
    private val friendshipRepository: FriendshipRepository,
    private val currentUserId: Int,
    private val otherUserId: Int
) : ViewModel() {
    private val _otherUser = MutableLiveData<UserData>()
    val otherUser: LiveData<UserData> = _otherUser

    private val _isFriend = MutableLiveData(false)
    val isFriend: LiveData<Boolean> = _isFriend

    init {
        observeOtherUser()
        refreshUserProfile()
    }

    fun observeOtherUser() {
        _otherUser.observeForever {
            refreshIsFriend()
        }
    }

    fun refreshUserProfile() {
        val userProfile = userRepository.fetchUserById(otherUserId)
        userProfile?.let {
            _otherUser.postValue(it)
        }
    }

    fun refreshIsFriend() {
        _isFriend.postValue(friendshipRepository.isFriendById(currentUserId, otherUserId))
    }

    fun addFriend() {
        friendshipRepository.addFriendshipById(currentUserId, otherUserId)
        refreshIsFriend()
    }

    fun removeFriend() {
        friendshipRepository.removeFriendshipById(currentUserId, otherUserId)
        refreshIsFriend()
    }

    fun getCurrentUserId(): Int {
        return currentUserId
    }

    fun getOtherUserId(): Int {
        return otherUserId
    }
}