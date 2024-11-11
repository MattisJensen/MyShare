package dk.sdu.myshare.presentation.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.Views
import dk.sdu.myshare.presentation.group.mygroups.view.MyGroupsViewRoot
import dk.sdu.myshare.presentation.group.opengroup.view.OpenGroupView
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
    Column(
        modifier = modifier,
        content = {
           MyGroupsViewRoot(navController, ViewModelFactory.getMyGroupsViewModel(1))
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