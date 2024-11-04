package dk.sdu.myshare.business.utility

import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserRepository
import dk.sdu.myshare.presentation.group.selectedgroup.viewmodel.GroupViewModel

class DependencyInjectionContainer {
    /* repos */
    val userRepository: UserRepository
    val groupRepository: GroupRepository

    /* view models */
    val groupViewModel: GroupViewModel

    init {
        userRepository = UserRepository()
        groupRepository = GroupRepository()
        groupViewModel = GroupViewModel(userRepository, groupRepository)
    }
}