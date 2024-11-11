package dk.sdu.myshare.business.utility

import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.mygroups.viewmodel.MyGroupsViewModel
import dk.sdu.myshare.presentation.group.opengroup.viewmodel.OpenGroupViewModel
import dk.sdu.myshare.presentation.home.viewmodel.HomeViewModel
import dk.sdu.myshare.presentation.profile.otherprofile.viewmodel.OtherProfileViewModel

object ViewModelFactory {
    fun getHomeViewModel(): HomeViewModel {
        return HomeViewModel()
    }

    fun getMyGroupsViewModel(userId: Int): MyGroupsViewModel {
        return MyGroupsViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, userId)
    }

    fun getOpenGroupViewModel(groupId: Int): OpenGroupViewModel {
        return OpenGroupViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, groupId)
    }

    fun getManageGroupMembersViewModel(groupId: Int): ManageGroupMemberViewModel {
        return ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, groupId)
    }

    fun getOtherProfileViewModel(profileUserId: Int): OtherProfileViewModel {
        return OtherProfileViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, DependencyInjectionContainer.friendshipRepository, profileUserId)
    }
}