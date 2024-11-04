package dk.sdu.myshare.presentation.group.managegroupmember.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import dk.sdu.myshare.business.model.user.UserData
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.managegroupmember.viewmodel.ManageGroupMemberViewModel
import dk.sdu.myshare.presentation.group.selectedgroup.view.GroupMemberIcon
import dk.sdu.myshare.presentation.group.selectedgroup.view.GroupMembers
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
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
                                {} // TODO: Open user profile
                            )

                            Spacer(modifier = Modifier.width(13.dp))

                            Text(user.name, modifier = Modifier.weight(1f))
                            if (!isInGroup) {
                                EditButton(
                                    onClick = {
                                        viewModel.addUserToGroup(user.id)
                                        onClose()
                                    },
                                    color = Color(0xFF77DD77),
                                    icon = Icons.Default.Add
                                )
                            } else {
                                EditButton(
                                    onClick = {
                                        viewModel.removeUserFromGroup(user.id)
                                        onClose()
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
fun UserListPreview() {
    val manageGroupMemberViewModel = ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository)
    UserList(filteredCandidates = listOf(Pair(UserData(1, "John Doe", "test@mail.dk", "123"), false)), viewModel = manageGroupMemberViewModel, {})
}
//@Composable
//fun PreviewUserSearchView() {
//    val manageGroupMemberViewModel = ManageGroupMemberViewModel(DependencyInjectionContainer.userRepository, DependencyInjectionContainer.groupRepository)
//    UserSearchView(viewModel = manageGroupMemberViewModel, {})
//}