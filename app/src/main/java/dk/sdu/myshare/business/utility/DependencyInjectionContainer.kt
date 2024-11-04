package dk.sdu.myshare.business.utility

import dk.sdu.myshare.business.model.group.GroupRepository
import dk.sdu.myshare.business.model.user.UserRepository
import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.selectedgroup.viewmodel.SelectedGroupViewModel

object DependencyInjectionContainer {
    /* repos */
    val userRepository: UserRepository
    val groupRepository: GroupRepository

    /* view models */
    val selectedGroupViewModel: SelectedGroupViewModel
    val manageGroupMemberViewModel: ManageGroupMemberViewModel

    init {
        /* repos */
        userRepository = UserRepository()
        groupRepository = GroupRepository()

        /* view models */
        selectedGroupViewModel = SelectedGroupViewModel(userRepository, groupRepository)
        manageGroupMemberViewModel = ManageGroupMemberViewModel(userRepository, groupRepository)
    }
}