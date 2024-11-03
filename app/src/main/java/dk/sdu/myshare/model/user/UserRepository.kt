package dk.sdu.myshare.model.user

import dk.sdu.myshare.model.database.mock.LocalData
import dk.sdu.myshare.model.database.mock.MockDBUser

class UserRepository {
    fun fetchUserDataByID(id: Int): UserData? {
        LocalData.getUserById(id)?.let {
            return UserData(
                id = it.id,
                name = it.name,
                email = it.email,
                password = it.password
            )
        }

        return null
    }

    fun addUser(user: UserData): Boolean {
        return LocalData.addUser(
            MockDBUser(
                id = user.id,
                name = user.name,
                email = user.email,
                password = user.password
            )
        )
    }
}