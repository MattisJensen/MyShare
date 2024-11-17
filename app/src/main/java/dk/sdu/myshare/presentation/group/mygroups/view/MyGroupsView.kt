package dk.sdu.myshare.presentation.group.mygroups.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.Views
import dk.sdu.myshare.presentation.group.mygroups.viewmodel.MyGroupsViewModel

@Composable
fun MyGroupsViewRoot(
    navController: NavHostController,
    viewModel: MyGroupsViewModel
) {
    MyGroupsView(navController, viewModel)
}

@Composable
fun MyGroupsView(navController: NavHostController, viewModel: MyGroupsViewModel) {
    Column(
        modifier = Modifier.
        padding(16.dp),
        content = {
            MyGroupsHeader()
            GroupList(navController, viewModel)
        }
    )
}

@Composable
fun MyGroupsHeader() {
    Text(
        text = "Groups",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun GroupList(navController: NavHostController, viewModel: MyGroupsViewModel) {
    val myGroups by viewModel.myGroups.observeAsState(emptyList())

    LazyColumn {
        items(myGroups) { group ->
            ListItem(
                headlineContent = { Text(group.name) },
                supportingContent = { Text("DKK ${group.groupBalance}") },
                leadingContent = {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null
                    )
                },
                modifier = Modifier.clickable { navController.navigate(Views.OpenGroup.createRoute(group.id)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMyGroupsViewRoot() {
    val myGroupsViewModel: MyGroupsViewModel = ViewModelFactory.getMyGroupsViewModel(1)
    val navController = rememberNavController()
    MyGroupsViewRoot(navController, myGroupsViewModel)
}