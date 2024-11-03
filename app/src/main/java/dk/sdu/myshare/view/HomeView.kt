package dk.sdu.myshare.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dk.sdu.myshare.di.DependencyInjectionContainer

@Composable
fun HomeView(modifier: Modifier = Modifier, dependencyInjectionContainer: DependencyInjectionContainer) {
    GroupView(dependencyInjectionContainer.groupViewModel)
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeView() {
    val dependencyInjectionContainer = DependencyInjectionContainer()
    HomeView(modifier = Modifier, dependencyInjectionContainer = dependencyInjectionContainer)
}