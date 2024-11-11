package dk.sdu.myshare.presentation

sealed class Views(val route: String) {
    object LoginInput : Views("login")
    object Home : Views("home")
    object MyProfile : Views("myprofile")
    object OtherProfile : Views("otherprofile") {
        val key: String = "userId"
        fun createRoute(userId: Int) = "otherprofile/$userId"
    }

    object OpenGroup : Views("opengroup/{groupId}") {
        val key: String = "groupId"
        fun createRoute(groupId: Int) = "opengroup/$groupId"
    }

    object ManageGroupMembers : Views("managegroupmembers/{groupId}") {
        val key: String = "groupId"
        fun createRoute(groupId: Int) = "managegroupmembers/$groupId"
    }
}