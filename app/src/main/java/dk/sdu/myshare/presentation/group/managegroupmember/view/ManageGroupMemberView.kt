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
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.opengroup.viewmodel.GroupViewModel

@Composable
fun UserSearchView(viewModel: GroupViewModel) {
    val addUserToGroupCandidates by viewModel.addUserToGroupCandidates.observeAsState(emptyMap())
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search Users") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(addUserToGroupCandidates.filter { it.key.name.contains(searchQuery.value, ignoreCase = true) }.toList()) { (user, isInGroup) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(user.name, modifier = Modifier.weight(1f))
                    if (!isInGroup) {
                        Button(
                            onClick = {
                                viewModel.addUserToGroup(user.id)
                                viewModel.setShowUserSearch(false)
                            },
                            content = {
                                Text("Add")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserSearchView() {
    val dependencyInjectionContainer: DependencyInjectionContainer = DependencyInjectionContainer()
    val groupViewModel: GroupViewModel = dependencyInjectionContainer.groupViewModel
    UserSearchView(viewModel = groupViewModel)
}