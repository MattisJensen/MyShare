package dk.sdu.myshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.Views
import dk.sdu.myshare.presentation.group.addtogroup.view.AddUserToGroupViewRoot
import dk.sdu.myshare.presentation.group.managegroupmember.view.ManageGroupMemberViewRoot
import dk.sdu.myshare.presentation.group.opengroup.view.OpenGroupViewRoot
import dk.sdu.myshare.ui.theme.MyShareTheme
import dk.sdu.myshare.presentation.home.view.HomeViewRoot
import dk.sdu.myshare.presentation.profile.otherprofile.view.OtherProfileViewRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    MyShareTheme {
        val navController = rememberNavController()
        Scaffold(
            modifier = Modifier.fillMaxSize(),
//            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavigationGraph(navController, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Views.Home.route, modifier = modifier) {
        composable(Views.Home.route) {
            HomeViewRoot(navController, ViewModelFactory.getHomeViewModel())
        }

        composable(Views.OtherProfile.route ) { backStackEntry ->
            val currentUserId = backStackEntry.arguments?.getString(Views.OtherProfile.key1)?.toInt() ?: 0
            val otherUserId = backStackEntry.arguments?.getString(Views.OtherProfile.key2)?.toInt() ?: 0
            OtherProfileViewRoot(navController, ViewModelFactory.getOtherProfileViewModel(currentUserId, otherUserId))
        }

        composable(Views.OpenGroup.route) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString(Views.OpenGroup.key)?.toInt() ?: 0
            OpenGroupViewRoot(navController, ViewModelFactory.getOpenGroupViewModel(groupId))
        }

        composable(Views.ManageGroupMembers.route) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString(Views.ManageGroupMembers.key)?.toInt() ?: 0
            ManageGroupMemberViewRoot(navController, ViewModelFactory.getManageGroupMembersViewModel(groupId))
        }

        composable(Views.AddUserToGroup.route) { backStackEntry ->
            val currentUserId = backStackEntry.arguments?.getString(Views.AddUserToGroup.key1)?.toInt() ?: 0
            val otherUserId = backStackEntry.arguments?.getString(Views.AddUserToGroup.key2)?.toInt() ?: 0
            AddUserToGroupViewRoot(navController, ViewModelFactory.getAddUserToGroupViewModel(currentUserId, otherUserId))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    MyApp()
}
