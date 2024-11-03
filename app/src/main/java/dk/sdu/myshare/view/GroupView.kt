package dk.sdu.myshare.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dk.sdu.myshare.di.DependencyInjectionContainer
import dk.sdu.myshare.viewmodel.GroupViewModel

@Composable
fun GroupView(viewModel: GroupViewModel) {
    val userData = viewModel.userData.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            viewModel.getUserData()
        }) {
            Text(text = "Get Data")
        }

        userData.value?.name?.let {
            Text(text = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGroupView() {
    val dependencyInjectionContainer: DependencyInjectionContainer = DependencyInjectionContainer()
    val groupViewModel: GroupViewModel = dependencyInjectionContainer.groupViewModel
    GroupView(viewModel = groupViewModel)
}