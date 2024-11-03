package dk.sdu.myshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dk.sdu.myshare.di.DependencyInjectionContainer
import dk.sdu.myshare.ui.theme.MyShareTheme
import dk.sdu.myshare.view.HomeView

class MainActivity : ComponentActivity() {
    val dependencyInjectionContainer: DependencyInjectionContainer

    init {
        dependencyInjectionContainer = DependencyInjectionContainer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyShareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeView(Modifier.padding(innerPadding), dependencyInjectionContainer)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    val dependencyInjectionContainer = DependencyInjectionContainer()

    MyShareTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            HomeView(Modifier.padding(innerPadding), dependencyInjectionContainer)
        }
    }
}
