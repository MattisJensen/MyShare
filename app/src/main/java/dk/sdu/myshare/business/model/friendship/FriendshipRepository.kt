package dk.sdu.myshare.business.model.friendship

import dk.sdu.myshare.data.database.mock.LocalData

class FriendshipRepository {
    fun fetchFriendshipDataByID(id: Int): List<FriendshipData> {
        LocalData.getFriendshipsForUserById(id)?.let {
            return it.map { friendship ->
                FriendshipData(
                    userId1 = friendship.userId1,
                    userId2 = friendship.userId2
                )
            }
        }

        return emptyList()
    }

    fun addFriendshipById(userId1: Int, userId2: Int): Boolean {
        return LocalData.addFriendshipById(userId1, userId2)
    }

    fun removeFriendshipById(userId1: Int, userId2: Int): Boolean {
        return LocalData.deleteFriendshipById(userId1, userId2)
    }

    fun isFriendById(userId1: Int, userId2: Int): Boolean {
        return LocalData.isFriendById(userId1, userId2)
    }
}