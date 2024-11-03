package dk.sdu.myshare.model

class UserRepository {
    fun fetchUserData(): UserData {
        return UserData(
            name = "John Doe",
            email = "john.doe@example.com",
            password = "password123"
        )
    }
}