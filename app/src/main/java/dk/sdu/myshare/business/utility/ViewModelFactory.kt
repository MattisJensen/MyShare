package dk.sdu.myshare.business.utility

import dk.sdu.myshare.presentation.friends.viewmodel.FriendsViewModel
import dk.sdu.myshare.presentation.group.addtogroup.viewmodel.AddUserToGroupViewModel
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

    fun getOtherProfileViewModel(currentUserId: Int, otherUserId: Int): OtherProfileViewModel {
        return OtherProfileViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, DependencyInjectionContainer.friendshipRepository, currentUserId, otherUserId)
    }

    fun getAddUserToGroupViewModel(currentUserId: Int, otherUserId: Int): AddUserToGroupViewModel {
        return AddUserToGroupViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository, currentUserId, otherUserId)
    }

    fun getFriendsViewModel(): FriendsViewModel {
        return FriendsViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.friendshipRepository)
    }
}