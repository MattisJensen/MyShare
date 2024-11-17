package dk.sdu.myshare.presentation.friends.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dk.sdu.myshare.presentation.Views
import dk.sdu.myshare.presentation.friends.viewmodel.FriendsViewModel
import dk.sdu.myshare.presentation.group.managegroupmember.view.EditButton
import dk.sdu.myshare.presentation.group.opengroup.view.GroupMemberIcon

@Composable
fun FriendsViewRoot(
    navController: NavController,
    viewModel: FriendsViewModel
) {
    FriendsView(navController, viewModel)
}

@Composable
fun FriendsView(
    navController: NavController,
    viewModel: FriendsViewModel
) {
    Column(
        modifier = Modifier.
        padding(16.dp),
        content = {
            FriendsHeader()
            FriendsList(navController, viewModel)
        }
    )
}

@Composable
fun FriendsHeader() {
    Text(
        text = "Friends",
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun FriendsList(
    navController: NavController,
    viewModel: FriendsViewModel
) {
    val friends = viewModel.friends.observeAsState(emptyList())
    LazyColumn {
        items(friends.value) { user ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .clickable { navController.navigate(Views.OtherProfile.createRoute(viewModel.getCurrentUserId(), user.id)) },

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
                            EditButton(
                                onClick = {
                                    viewModel.removeFriend(user.id)
                                },
                                color = Color(0xFFFF6961),
                                icon = Icons.Default.Delete
                            )

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