package dk.sdu.myshare.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dk.sdu.myshare.model.UserData
import dk.sdu.myshare.model.UserRepository
import kotlin.random.Random

class GroupViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userData: MutableLiveData<UserData> = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    fun getUserData() {
        try {
            val userDataResult: UserData = userRepository.fetchUserData()
            _userData.postValue(userDataResult)
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