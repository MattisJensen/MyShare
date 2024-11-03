package dk.sdu.myshare.model.database.mock

/**
 * Using object in Kotlin creates a singleton, which ensures that there is only one instance of LocalData throughout the application.
 * This is useful for a mock database because it simulates a single, consistent data source.
 */
object LocalData {
    private val users = mutableListOf<MockDBUser>()
    private val groups = mutableListOf<MockDBGroup>()

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

    fun addUserToGroup(userId: Int, groupId: Int): Boolean {
        val group: MockDBGroup = getGroupById(groupId) ?: return false
        val user: MockDBUser = getUserById(userId) ?: return false

        if (group.members.contains(userId)) {
            return false // User already in group
        }

        group.members.add(userId)
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
            users.add(MockDBUser(
                id = i,
                name = fullName,
                email = "$firstName.$lastName@example.com",
                password = "password$i"
            ))
        }

        // add mock groups with fun names and 2 to 7 members
        val groupNames = listOf("Unicorn Brewery", "Dragon Riders", "Phoenix Club", "Mystic Wizards", "Galactic Explorers", "Girls Trip 💁🏻‍♀️", "Adventure Squad", "Book Club", "Fitness Friends", "Travel Buddies")
        for (i in 1..10) {
            val memberCount = (2..16).random()
            val members = (1..40).shuffled().take(memberCount).toMutableList()
            groups.add(MockDBGroup(
                id = i,
                name = groupNames[(i - 1) % groupNames.size],
                groupBalance = 0.0,
                members = members
            ))
        }
    }
}