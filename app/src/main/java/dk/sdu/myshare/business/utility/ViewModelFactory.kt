package dk.sdu.myshare.business.utility

import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.selectedgroup.viewmodel.SelectedGroupViewModel
import dk.sdu.myshare.presentation.home.viewmodel.HomeViewModel
import dk.sdu.myshare.presentation.profile.viewmodel.ProfileViewModel

object ViewModelFactory {
    fun getHomeViewModel(): HomeViewModel {
        return HomeViewModel()
    }

    fun getSelectedGroupViewModel(): SelectedGroupViewModel {
        return SelectedGroupViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository)
    }

    fun getManageGroupMemberViewModel(): ManageGroupMemberViewModel {
        return ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository)
    }

    fun getProfileViewModel(profileUserId: Int): ProfileViewModel {
        return ProfileViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, DependencyInjectionContainer.friendshipRepository, profileUserId)
    }
}