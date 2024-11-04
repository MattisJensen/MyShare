package dk.sdu.myshare.business.model.user

import dk.sdu.myshare.data.database.mock.LocalData
import dk.sdu.myshare.data.database.mock.tables.MockDBUser

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

    fun fetchAllUsers(): List<UserData>? {
        return LocalData.getAllUsers()?.map {
            UserData(
                id = it.id,
                name = it.name,
                email = it.email,
                password = it.password
            )
        }
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