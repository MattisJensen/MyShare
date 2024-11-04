package dk.sdu.myshare.presentation.group.selectedgroup.view

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
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.managegroupmember.view.UserSearchView
import dk.sdu.myshare.presentation.group.selectedgroup.viewmodel.SelectedGroupViewModel

@Composable
fun GroupView(viewModel: SelectedGroupViewModel) {
    if (viewModel.showUserSearch.observeAsState(false).value) {
        UserSearchView(viewModel = DependencyInjectionContainer.manageGroupMemberViewModel, {})
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.hsv(240f, 0.1f, 1f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            content = {
                GroupHeader(viewModel = viewModel)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupView() {
    val selectedGroupViewModel: SelectedGroupViewModel = DependencyInjectionContainer.selectedGroupViewModel
    GroupView(viewModel = selectedGroupViewModel)
}