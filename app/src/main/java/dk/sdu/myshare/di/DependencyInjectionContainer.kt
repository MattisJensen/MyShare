package dk.sdu.myshare.di

import dk.sdu.myshare.model.group.GroupRepository
import dk.sdu.myshare.model.user.UserRepository
import dk.sdu.myshare.viewmodel.GroupViewModel

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