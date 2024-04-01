package co.edu.udea.compumovil.gr03_20241.lab1

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import co.edu.udea.compumovil.gr03_20241.lab1.ui.theme.LabsCM20241Gr03Theme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr03_20241.lab1.models.PersonalViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val personal: PersonalViewModel by viewModels()
        setContent {
            LabsCM20241Gr03Theme {
                Navigation(personal= personal)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    personal: PersonalViewModel = viewModel()
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: DestinationItem.HOME.name
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    val routes = getDestinations(navigationActions, personal)

    Orientation(landscape = {
        Row(
        ) {
            NavigationRail(
                modifier = Modifier
                    .shadow(elevation = 4.dp),
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                routes.forEach { item ->
                    if(item.show) {
                        NavigationRailItem(
                            modifier = Modifier.padding(10.dp),
                            icon = {
                                Icon(
                                    if (currentRoute == item.key) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = stringResource(id = item.title)
                                )
                            },
                            label = { Text(stringResource(id = item.title)) },
                            selected = currentRoute == item.key,
                            onClick = { item.functions.invoke() }
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
            ) {
                NavHost(
                    navController = navController, startDestination = DestinationItem.HOME.key
                ) {
                    composable(DestinationItem.HOME.key) {
                        HomeScreen()
                    }

                    composable(DestinationItem.PERSON.key) {
                        PersonScreen(navController= navController, personal = personal)
                    }

                    composable(DestinationItem.CONTACT.key) {
                        ContactScreen(navController= navController, personal = personal)
                    }

                    composable(DestinationItem.SUCCESS.key) {
                        SuccessScreen(navController= navController, personal = personal)
                    }
                }
            }
        }
    }) {
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                NavBar(route = currentRoute, items = routes)
            },
        ) {
            NavHost(
                navController = navController, startDestination = DestinationItem.HOME.key, modifier = modifier.padding(it)
            ) {
                composable(DestinationItem.HOME.key) {
                    HomeScreen()
                }

                composable(DestinationItem.PERSON.key) {
                    PersonScreen(navController= navController, personal = personal)
                }

                composable(DestinationItem.CONTACT.key) {
                    ContactScreen(navController= navController, personal = personal)
                }

                composable(DestinationItem.SUCCESS.key) {
                    SuccessScreen(navController= navController, personal = personal)
                }
            }
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun DefaultPreview() {
    LabsCM20241Gr03Theme {
        Navigation()
    }
}