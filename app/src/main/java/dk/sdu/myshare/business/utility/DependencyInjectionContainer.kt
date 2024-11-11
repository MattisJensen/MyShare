package dk.sdu.myshare.business.utility

import dk.sdu.myshare.business.model.friendship.FriendshipRepository
import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserRepository

object DependencyInjectionContainer {
    /* repos */
    val userRepository: UserRepository
    val groupRepository: GroupRepository
    val friendshipRepository: FriendshipRepository

    init {
        /* repos */
        userRepository = UserRepository()
        groupRepository = GroupRepository()
        friendshipRepository = FriendshipRepository()
    }
}