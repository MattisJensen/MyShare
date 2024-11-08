package dk.sdu.myshare.presentation.profile.view

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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.sdu.myshare.R
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.profile.viewmodel.ProfileViewModel


@Composable
fun ProfileView(viewModel: ProfileViewModel) {
    val userProfile = viewModel.userProfile.observeAsState()

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

//                if (viewModel.isFriend.value == true) {
                Button(onClick = { viewModel.addFriend() }) {
                    Row (
                        horizontalArrangement =
                            Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Add Friend")
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Friend",
                            modifier = Modifier.size(16.dp)
                        )
                    }
//                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun ProfileViewPreview() {
    ProfileView(ViewModelFactory.getProfileViewModel(1))
}