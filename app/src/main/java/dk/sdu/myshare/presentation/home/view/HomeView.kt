package dk.sdu.myshare.presentation.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dk.sdu.myshare.business.utility.DependencyInjectionContainer
import dk.sdu.myshare.presentation.group.selectedgroup.view.GroupView

@Composable
fun HomeView(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        content = {
            GroupView(DependencyInjectionContainer.selectedGroupViewModel)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeView() {
    HomeView(modifier = Modifier)
}