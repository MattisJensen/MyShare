package dk.sdu.myshare.presentation.group.managegroupmember.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.Views
import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.opengroup.view.GroupMemberIcon

@Composable
fun ManageGroupMemberViewRoot(
    navController: NavHostController,
    viewModel: ManageGroupMemberViewModel
) {
    ManageGroupMemberView(navController, viewModel)
}

@Composable
fun ManageGroupMemberView(
    navController: NavHostController,
    viewModel: ManageGroupMemberViewModel
) {
    val filteredMembers by viewModel.filteredMembers.observeAsState(emptyList())
    val filteredCandidates by viewModel.filteredCandidates.observeAsState(emptyList())
    val searchQuery = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),

        content = {
            SearchUserField(
                value = searchQuery.value,
                onValueChange = {
                    searchQuery.value = it
                    viewModel.filterUsers(it)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            UserList(
                navController,
                viewModel,
                filteredMembers,
                filteredCandidates)
        }
    )
}

@Composable
fun SearchUserField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Search Users") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun UserList(
    navController: NavHostController,
    viewModel: ManageGroupMemberViewModel,
    members: List<UserData>,
    usersToAdd: List<UserData>,
) {
    LazyColumn {
        items(members + usersToAdd) { user ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate(Views.OtherProfile.createRoute(viewModel.getCurrentUserId(), user.id))  },

                content = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,

                        content = {
                            GroupMemberIcon(
                                name = viewModel.getNameInitials(user.name),
                                color = viewModel.getTemporaryUserColor(user.id),
                                { navController.navigate(Views.OtherProfile.createRoute(viewModel.getCurrentUserId(), user.id)) }
                            )

                            Spacer(modifier = Modifier.width(13.dp))

                            Text(user.name, modifier = Modifier.weight(1f))
                            if (usersToAdd.contains(user)) {
                                EditButton(
                                    onClick = {
                                        viewModel.addUserToGroup(user.id)
                                    },
                                    color = Color(0xFF77DD77),
                                    icon = Icons.Default.Add
                                )
                            } else {
                                EditButton(
                                    onClick = {
                                        viewModel.removeUserFromGroup(user.id)
                                    },
                                    color = Color(0xFFFF6961),
                                    icon = Icons.Default.Delete
                                )
                            }

                            Spacer(modifier = Modifier.width(13.dp))
                        }
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(0.2.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.LightGray
                                    ),
                                    startX = 44f,
                                    endX = 400f
                                )
                            )
                    )
                }
            )
        }
    }
}

@Composable
fun EditButton(onClick: () -> Unit, color: Color, icon: ImageVector) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
        content = {
            Icon(
                imageVector = icon,
                tint = Color.White,
                contentDescription = "Edit Icon",
                modifier = Modifier
                    .fillMaxWidth(0.55f)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewUserSearchView() {
    val manageGroupMemberViewModel = ViewModelFactory.getManageGroupMembersViewModel(1)
    val navController = rememberNavController()
    ManageGroupMemberViewRoot(navController, manageGroupMemberViewModel)
}