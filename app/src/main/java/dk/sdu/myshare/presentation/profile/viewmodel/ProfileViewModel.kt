package dk.sdu.myshare.presentation.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.business.model.friendship.FriendshipRepository
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.model.user.UserRepository

class ProfileViewModel(private val userRepository: UserRepository, private val groupRepository: GroupRepository, private val friendshipRepository: FriendshipRepository, private val profileUserId: Int) : ViewModel() {
    private val _userProfile = MutableLiveData<UserData>()
    val userProfile: LiveData<UserData> = _userProfile

    private val _isFriend = MutableLiveData(false)
    val isFriend: LiveData<Boolean> = _isFriend

    init {
        refreshUserProfile() // FIXME: Hardcoded user ID instead of using the current logged in user
        refreshIsFriend()
    }

    fun refreshUserProfile() {
        val userProfile = userRepository.fetchUserByID(profileUserId)
        userProfile?.let {
            _userProfile.postValue(it)
            refreshIsFriend()
        }
    }

    fun refreshIsFriend() {
        val currentUserId = 1 // FIXME: Hardcoded user ID instead of using the current logged in user
        _isFriend.postValue(friendshipRepository.isFriend(currentUserId, profileUserId))
    }

    fun addFriend() {
        val currentUserId = 1 // FIXME: Hardcoded user ID instead of using the current logged in user
        friendshipRepository.addFriendship(currentUserId, profileUserId)
    }

   
}