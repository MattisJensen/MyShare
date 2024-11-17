package dk.sdu.myshare.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.friends.view.FriendsView
import dk.sdu.myshare.presentation.group.mygroups.view.MyGroupsViewRoot
import dk.sdu.myshare.presentation.home.viewmodel.HomeViewModel

@Composable
fun HomeViewRoot(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    HomeView(navController = navController)
}

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedView by remember { mutableStateOf("Groups") }

    Box(
        modifier = modifier.fillMaxHeight(),
        content = {
            Column(
                content = {
                    if (selectedView == "Groups") {
                        MyGroupsViewRoot(navController, ViewModelFactory.getMyGroupsViewModel(1))
                    } else if (selectedView == "Friends") {
                        FriendsView(navController, ViewModelFactory.getFriendsViewModel())
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                content = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Groups",
                        modifier = Modifier
                            .weight(1f)
                            .height(28.dp)
                            .clickable { selectedView = "Groups" }
                    )
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Friends",
                        modifier = Modifier
                            .weight(1f)
                            .height(28.dp)
                            .clickable { selectedView = "Friends" }
                    )
                }
            )
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeViewRoot() {
    val homeViewModel: HomeViewModel = ViewModelFactory.getHomeViewModel()
    val navController = rememberNavController()
    HomeViewRoot(navController, homeViewModel)
}