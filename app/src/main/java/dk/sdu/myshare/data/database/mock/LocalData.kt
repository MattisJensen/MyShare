package dk.sdu.myshare.data.database.mock

import dk.sdu.myshare.data.database.mock.tables.MockDBFriendship
import dk.sdu.myshare.data.database.mock.tables.MockDBGroup
import dk.sdu.myshare.data.database.mock.tables.MockDBUser

/**
 * Using object in Kotlin creates a singleton, which ensures that there is only one instance of LocalData throughout the application.
 * This is useful for a mock database because it simulates a single, consistent data source.
 */
object LocalData {
    private val users = mutableListOf<MockDBUser>()
    private val groups = mutableListOf<MockDBGroup>()
    private val friendships = mutableListOf<MockDBFriendship>()


    /* User functions */
    fun addUser(user: MockDBUser): Boolean {
        return if (users.any { it.id == user.id }) {
            false // User already exists
        } else {
            users.add(MockDBUser(user.id, user.name, user.email, user.password))
            true
        }
    }

    fun getUserById(id: Int): MockDBUser? {
        return users.find { it.id == id }
    }

    fun getAllUsers(): List<MockDBUser> {
        return users
    }

    /* Group functions */
    fun addGroup(group: MockDBGroup): Boolean {
        return if (groups.any { it.id == group.id }) {
            false // Group already exists
        } else {
            groups.add(group)
            true
        }
    }

    fun getGroupById(id: Int): MockDBGroup? {
        return groups.find { it.id == id }
    }

    fun addUserToGroupById(userId: Int, groupId: Int): Boolean {
        val group: MockDBGroup = getGroupById(groupId) ?: return false
        val user: MockDBUser = getUserById(userId) ?: return false

        if (group.members.contains(userId)) {
            return false // User already in group
        }

        group.members.add(userId)
        return true
    }

    fun removeUserFromGroupById(userId: Int, groupId: Int): Boolean {
        val group: MockDBGroup = getGroupById(groupId) ?: return false
        val user: MockDBUser = getUserById(userId) ?: return false

        if (!group.members.contains(userId)) {
            return false // User not in group
        }

        group.members.remove(userId)
        return true
    }

    fun getGroupsWhereUserIsMemberById(userId: Int): List<MockDBGroup> {
        val user: MockDBUser = getUserById(userId) ?: return emptyList()
        return groups.filter { it.members.contains(userId) }
    }

    /* Friendship functions */
    fun getFriendshipsForUserById(userId: Int): List<MockDBFriendship> {
        getUserById(userId)?.let {
            return friendships.filter { it.userId1 == userId || it.userId2 == userId }
        } ?: return emptyList()
    }

    fun isFriendById(user1Id: Int, user2Id: Int): Boolean {
        return friendships.any { (it.userId1 == user1Id && it.userId2 == user2Id) || (it.userId1 == user2Id && it.userId2 == user1Id) }
    }

    fun addFriendshipById(user1Id: Int, user2Id: Int): Boolean {
        if (user1Id == user2Id) {
            return false
        }

        // check if users exist
        val user1: MockDBUser = getUserById(user1Id) ?: return false
        val user2: MockDBUser = getUserById(user2Id) ?: return false

        if (isFriendById(user1Id, user2Id)) {
            return false
        }

        friendships.add(MockDBFriendship(user1Id, user2Id))
        return true
    }

    fun deleteFriendshipById(user1Id: Int, user2Id: Int): Boolean {
        if (user1Id == user2Id) {
            return false
        }

        // check if users exist
        val user1: MockDBUser = getUserById(user1Id) ?: return false
        val user2: MockDBUser = getUserById(user2Id) ?: return false

        if (!isFriendById(user1Id, user2Id)) {
            return false
        }

        friendships.removeIf { (it.userId1 == user1Id && it.userId2 == user2Id) || (it.userId1 == user2Id && it.userId2 == user1Id) }
        return true
    }


    init {
        // add mock users
        val firstNames = listOf("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Ivy", "Jack", "Karen", "Leo", "Mona", "Nina", "Oscar", "Paul", "Quinn", "Rita", "Sam", "Tina", "Uma", "Vince", "Wendy", "Xander", "Yara", "Zane")
        val lastNames = listOf("Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez")
        for (i in 1..40) {
            val firstName = firstNames[(i - 1) % firstNames.size]
            val lastName = lastNames[(i - 1) % lastNames.size]
            val fullName = "$firstName $lastName"
            users.add(
                MockDBUser(
                    id = i,
                    name = fullName,
                    email = "$firstName.$lastName@example.com",
                    password = "password$i"
                )
            )
        }

        // add mock groups with fun names and 2 to 7 members
        val groupNames = listOf("Girls Tour️", "Fitness Group 💪", "TV Weekend", "Book Club 📚", "Travel Buddies ✈️", "Study Group 📖", "Work Friends", "Family Stuff", "Cinema Tour", "Football Team ⚽")
        for (i in 1..10) {
            val memberCount = (2..16).random()
            val members = (1..40).shuffled().take(memberCount).toMutableList()
            groups.add(
                MockDBGroup(
                    id = i,
                    name = groupNames[(i - 1) % groupNames.size],
                    groupBalance = 0.0,
                    members = members
                )
            )
        }

        // add mock friendships
        for (i in 1..40) {
            val friendCount = (1..5).random()
            val friends = (1..40).shuffled().take(friendCount)
            friends.forEach { friendId ->
                friendships.add(MockDBFriendship(i, friendId))
            }
        }
    }
}