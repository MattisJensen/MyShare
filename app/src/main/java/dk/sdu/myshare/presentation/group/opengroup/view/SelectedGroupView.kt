package dk.sdu.myshare.presentation.group.opengroup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.managegroupmember.view.UserSearchView
import dk.sdu.myshare.presentation.group.opengroup.viewmodel.GroupViewModel

@Composable
fun GroupView(viewModel: GroupViewModel) {
    if (viewModel.showUserSearch.observeAsState(false).value) {
        UserSearchView(viewModel = viewModel)
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

@Composable
fun GroupHeader(viewModel: GroupViewModel) {
    Column(
        modifier = Modifier
            .background(color = Color.hsv(0f, 0f, 1f))
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        content = {
            GroupInformation(viewModel = viewModel)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    BoxWithConstraints(
                        modifier = Modifier
                            .weight(1f),
                        content = {
                            val width = constraints.maxWidth.toFloat()
                            val startX = width - 150f
                            val endX = width

                            GroupMembers(viewModel = viewModel)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(40.dp)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.White
                                            ),
                                            startX = startX,
                                            endX = endX
                                        )
                                    )
                            )
                        }
                    )

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.hsv(0f, 0f, 0.4f),)
                            .clickable {
                                viewModel.refreshAddUserToGroupCandidates()
                                viewModel.setShowUserSearch(true)
                            },
                        contentAlignment = Alignment.Center,

                        content = {
                            Text(
                                text = "+",
//                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun GroupInformation(viewModel: GroupViewModel) {
    val groupData = viewModel.groupData.observeAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        content = {
            GroupInfoText(groupData.value?.name ?: "No group name yet")
            GroupInfoText("DKK ${groupData.value?.groupBalance ?: 0.00}")
        }
    )
}

@Composable
fun GroupInfoText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun GroupMembers(viewModel: GroupViewModel, modifier: Modifier = Modifier) {
    val userData = viewModel.userData.observeAsState()

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState()),

        content = {
            userData.value?.forEach {
                GroupMemberIcon(
                    name = viewModel.getNameLetters(it.name),
                    color = viewModel.getTemporaryUserColor(it.id),
                    {}
                )
                Spacer(modifier = Modifier.width(10.dp))
            }


        }
    )
}

@Composable
fun GroupMemberIcon(name: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center,

        content = {
            Text(
                text = name.take(2),
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupView() {
    val dependencyInjectionContainer: DependencyInjectionContainer = DependencyInjectionContainer()
    val groupViewModel: GroupViewModel = dependencyInjectionContainer.groupViewModel
    GroupView(viewModel = groupViewModel)
}