package dk.sdu.myshare.presentation

sealed class Views(val route: String) {
    object LoginInput : Views("login")
    object Home : Views("home")
    object MyProfile : Views("myprofile")
    object OtherProfile : Views("otherprofile/{currentUserId}/{otherUserId}") {
        val key1: String = "currentUserId"
        val key2: String = "otherUserId"
        fun createRoute(currentUserId: Int, otherUserId: Int) = "otherprofile/$currentUserId/$otherUserId"
    }

    object OpenGroup : Views("opengroup/{groupId}") {
        val key: String = "groupId"
        fun createRoute(groupId: Int) = "opengroup/$groupId"
    }

    object ManageGroupMembers : Views("managegroupmembers/{groupId}") {
        val key: String = "groupId"
        fun createRoute(groupId: Int) = "managegroupmembers/$groupId"
    }

    object AddUserToGroup : Views("addusertogroup/{currentUserId}/{otherUserId}") {
        val key1: String = "currentUserId"
        val key2: String = "otherUserId"
        fun createRoute(currentUserId: Int, otherUserId: Int) = "addusertogroup/$currentUserId/$otherUserId"
    }

    object FriendsView : Views("friends")
}