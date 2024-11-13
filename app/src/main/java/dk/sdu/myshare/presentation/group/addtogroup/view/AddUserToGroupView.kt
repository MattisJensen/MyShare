package dk.sdu.myshare.presentation.group.addtogroup.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dk.sdu.myshare.business.model.group.GroupData
import dk.sdu.myshare.business.utility.ViewModelFactory
import dk.sdu.myshare.presentation.group.addtogroup.viewmodel.AddUserToGroupViewModel
import dk.sdu.myshare.presentation.group.managegroupmember.view.EditButton

@Composable
fun AddUserToGroupViewRoot(
    navController: NavController,
    viewModel: AddUserToGroupViewModel
) {
    AddUserToGroupView(navController, viewModel)
}

@Composable
fun AddUserToGroupView(
    navController: NavController,
    viewModel: AddUserToGroupViewModel
) {
    val groupCandidates = viewModel.groupCandidates.observeAsState(emptyList())
    val commonGroups = viewModel.commonGroups.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        content = {
            AddToGroupHeader(viewModel)
            GroupCandidateList(viewModel, groupCandidates.value, commonGroups.value)
        }
    )
}

@Composable
fun AddToGroupHeader(viewModel: AddUserToGroupViewModel) {
    Text(
        "Add ${viewModel.getOtherUserName()} group",
        modifier = Modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun GroupCandidateList(
    viewModel: AddUserToGroupViewModel,
    groupCandidates: List<GroupData>,
    commonGroups: List<GroupData>,
) {
    LazyColumn {
        items(groupCandidates) { group ->
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
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = null,
                                modifier = Modifier.size(44.dp)
                            )

                            Spacer(modifier = Modifier.width(13.dp))

                            Text(group.name, modifier = Modifier.weight(1f))
                            if (!commonGroups.contains(group)) {
                                EditButton(
                                    onClick = {
                                        viewModel.addUserToGroup(group.id)
                                    },
                                    color = Color(0xFF77DD77),
                                    icon = Icons.Default.Add
                                )
                            } else {
                                EditButton(
                                    onClick = {
                                        viewModel.removeUserFromGroup(group.id)
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


@Preview
@Composable
fun AddToGroupViewPreview() {

    AddUserToGroupViewRoot(rememberNavController(), ViewModelFactory.getAddUserToGroupViewModel(1,2))
}

