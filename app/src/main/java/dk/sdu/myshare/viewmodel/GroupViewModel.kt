package dk.sdu.myshare.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.model.group.GroupData
import dk.sdu.myshare.model.group.GroupRepository
import dk.sdu.myshare.model.user.UserData
import dk.sdu.myshare.model.user.UserRepository
import kotlin.random.Random

class GroupViewModel(private val userRepository: UserRepository, private val groupRepository: GroupRepository) : ViewModel() {
    private val _userData: MutableLiveData<UserData> = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    private val _groupData: MutableLiveData<GroupData> = MutableLiveData<GroupData>()
    val groupData: LiveData<GroupData> = _groupData

    fun getUserData() {
        try {
            val userDataResult: UserData = userRepository.fetchUserData()
            _userData.postValue(userDataResult)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getGroupData() {
        try {
            val groupDataResult: GroupData = groupRepository.fetchGroupData()
            _groupData.postValue(groupDataResult)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getRandomPastelColor(): Color {
        val random = Random.Default
        val red = (random.nextInt(256) + 255) / 2
        val green = (random.nextInt(256) + 255) / 2
        val blue = (random.nextInt(256) + 255) / 2
        return Color(red, green, blue)
    }
}