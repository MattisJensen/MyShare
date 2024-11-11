package dk.sdu.myshare.presentation.group.opengroup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.group.managegroupmember.view.ManageGroupMemberView
import dk.sdu.myshare.presentation.group.opengroup.viewmodel.OpenGroupViewModel

@Composable
fun OpenGroupViewRoot(
    navController: NavHostController,
    viewModel: OpenGroupViewModel
) {
    OpenGroupView(navController, viewModel)
}

@Composable
fun OpenGroupView(navController: NavHostController, viewModel: OpenGroupViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.hsv(240f, 0.1f, 1f)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        content = {
            OpenGroupHeader(navController, viewModel)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupView() {
    val openGroupViewModel: OpenGroupViewModel = ViewModelFactory.getOpenGroupViewModel(1)
    val navController = rememberNavController()
    OpenGroupView(navController, openGroupViewModel)
}