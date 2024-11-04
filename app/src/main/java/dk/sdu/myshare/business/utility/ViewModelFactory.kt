package dk.sdu.myshare.business.utility

import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.selectedgroup.viewmodel.SelectedGroupViewModel
import dk.sdu.myshare.presentation.home.viewmodel.HomeViewModel
import dk.sdu.myshare.presentation.profile.viewmodel.ProfileViewModel

object ViewModelFactory {
    fun getHomeViewModel(): HomeViewModel {
        return HomeViewModel()
    }

    fun getSelectedGroupViewModel(groupId: Int): SelectedGroupViewModel {
        return SelectedGroupViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, groupId)
    }

    fun getManageGroupMemberViewModel(groupId: Int): ManageGroupMemberViewModel {
        return ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, groupId)
    }

    fun getProfileViewModel(profileUserId: Int): ProfileViewModel {
        return ProfileViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, DependencyInjectionContainer.friendshipRepository, profileUserId)
    }
}