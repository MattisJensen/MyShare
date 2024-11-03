package dk.sdu.myshare.model.group

import dk.sdu.myshare.model.user.UserData

class GroupRepository {
    fun fetchGroupData(): GroupData {
        return GroupData (
            id = 1,
            name = "My Group",
            groupBalance = 0.0,
            members = listOf(1, 2, 3)
        )
    }
}