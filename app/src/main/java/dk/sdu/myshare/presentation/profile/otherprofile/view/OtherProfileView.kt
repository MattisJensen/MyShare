package dk.sdu.myshare.presentation.profile.otherprofile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.R
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.Views
import dk.sdu.myshare.presentation.profile.otherprofile.viewmodel.OtherProfileViewModel

@Composable
fun OtherProfileViewRoot(
    navController: NavHostController,
    viewModel: OtherProfileViewModel
) {
    OtherProfileView(navController, viewModel)
}

@Composable
fun OtherProfileView(
    navController: NavHostController,
    viewModel: OtherProfileViewModel
) {
    val userProfile = viewModel.otherUser.observeAsState()
    val isFriend = viewModel.isFriend.observeAsState()

    userProfile.value?.let { profile ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            content = {
                Image(
//                    painter = painterResource(id = profile.profilePicture),
                    painter = painterResource(id = R.drawable.ic_launcher_background), // FIXME: Hardcoded profile picture
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(128.dp)
                        .background(Color.Gray, CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = profile.email)
                Spacer(modifier = Modifier.height(16.dp))

                if (isFriend.value == true) {
                    Button(onClick = { viewModel.removeFriend() }) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Remove Friend")
                        }
                    }
                } else {
                    Button(onClick = { viewModel.addFriend() }) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Add Friend")
                        }
                    }
                }

                Button(onClick = { navController.navigate(Views.AddUserToGroup.createRoute(viewModel.getCurrentUserId(), viewModel.getOtherUserId())) }) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Add to Group")
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun ProfileViewPreview() {
    val navController = rememberNavController()
    OtherProfileViewRoot(navController, ViewModelFactory.getOtherProfileViewModel(1, 2))
}