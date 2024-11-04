package dk.sdu.myshare.business.model.group

import dk.sdu.myshare.data.database.mock.LocalData

class GroupRepository {
    fun fetchGroupDataByID(id: Int): GroupData? {
        LocalData.getGroupById(id)?.let {
            return GroupData(
                id = it.id,
                name = it.name,
                groupBalance = it.groupBalance,
                members = it.members
            )
        }

        return null
    }

    fun addUserToGroup(userID: Int, groupID: Int): Boolean {
        return LocalData.addUserToGroup(userID, groupID)
    }

    fun removeUserFromGroup(userID: Int, groupID: Int): Boolean {
        return LocalData.removeUserFromGroup(userID, groupID)
    }
}