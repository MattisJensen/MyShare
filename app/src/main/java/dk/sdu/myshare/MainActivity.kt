package dk.sdu.myshare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dk.sdu.myshare.di.DependencyInjectionContainer
import dk.sdu.myshare.model.UserRepository
import dk.sdu.myshare.ui.theme.MyShareTheme
import dk.sdu.myshare.view.GroupView
import dk.sdu.myshare.view.HomeView
import dk.sdu.myshare.viewmodel.GroupViewModel
import kotlin.random.Random

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
