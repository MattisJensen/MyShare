package dk.sdu.myshare.presentation.group.managegroupmember.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.selectedgroup.viewmodel.SelectedGroupViewModel

@Composable
fun UserSearchView(viewModel: ManageGroupMemberViewModel, onClose: () -> Unit) {
    viewModel.onViewLoad()

    val addUserToGroupCandidates by viewModel.addUserToGroupCandidates.observeAsState(emptyMap())
    val searchQuery = remember { mutableStateOf("") }
    val filteredCandidates = addUserToGroupCandidates.filter { it.key.name.contains(searchQuery.value, ignoreCase = true) }.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),

        content = {
            SearchUserField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            UserList(filteredCandidates = filteredCandidates, viewModel = viewModel, onClose = onClose)
        }
    )
}

@Composable
fun SearchUserField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Search Users") },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun UserList(filteredCandidates: List<Pair<UserData, Boolean>>, viewModel: ManageGroupMemberViewModel, onClose: () -> Unit) {
    LazyColumn {
        items(filteredCandidates) { (user, isInGroup) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,

                content = {
                    Text(user.name, modifier = Modifier.weight(1f))
                    if (!isInGroup) {
                        AddUserButton(viewModel = viewModel, user = user, onClose)
                    }
                }
            )
        }
    }
}

@Composable
fun AddUserButton(viewModel: ManageGroupMemberViewModel, user: UserData, onClose: () -> Unit) {
    Button(
        onClick = {
            viewModel.addUserToGroup(user.id)
            onClose()
        },
        content = {
            Text("Add")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUserSearchView() {
    val manageGroupMemberViewModel = ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository)
    UserSearchView(viewModel = manageGroupMemberViewModel, {})
}