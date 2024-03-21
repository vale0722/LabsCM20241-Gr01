package co.edu.udea.compumovil.gr01_20241.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import co.edu.udea.compumovil.gr01_20241.lab1.ui.theme.LabsCM20241Gr01Theme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsCM20241Gr01Theme {
                Navigation()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: DestinationItem.HOME.name
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    val routes = getDestinations(navigationActions)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(id = DestinationItem.fromName(currentRoute)?.title!!)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            )
        },
        modifier = Modifier,
        bottomBar = {
            NavBar(route = currentRoute, items = routes)
        },
    ) {
        Column {
            NavHost(
                navController = navController, startDestination = DestinationItem.HOME.key, modifier = modifier.padding(it)
            ) {
                    composable(DestinationItem.HOME.key) {
                        HomeScreen()
                    }

                    composable(DestinationItem.PERSON.key) {
                        PersonScreen()
                    }

                    composable(DestinationItem.CONTACT.key) {
                        ContactScreen()
                    }
                }
            }
        }
    }


@Preview(showBackground = true, apiLevel = 33)
@Composable
fun DefaultPreview() {
    LabsCM20241Gr01Theme {
        Navigation()
    }
}