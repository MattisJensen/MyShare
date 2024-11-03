package dk.sdu.myshare.di

import dk.sdu.myshare.model.UserRepository
import dk.sdu.myshare.viewmodel.GroupViewModel

class DependencyInjectionContainer {
    /* repos */
    val userRepository: UserRepository

    /* view models */
    val groupViewModel: GroupViewModel

    init {
        userRepository = UserRepository()
        groupViewModel = GroupViewModel(userRepository)
    }
}