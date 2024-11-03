package dk.sdu.myshare.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dk.sdu.myshare.di.DependencyInjectionContainer

@Composable
fun HomeView(
    modifier: Modifier = Modifier,
    dependencyInjectionContainer: DependencyInjectionContainer
) {
    Column (
        modifier = modifier
    ) {
        GroupView(dependencyInjectionContainer.groupViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeView() {
    val dependencyInjectionContainer: DependencyInjectionContainer = DependencyInjectionContainer()
    HomeView(modifier = Modifier, dependencyInjectionContainer = dependencyInjectionContainer)
}