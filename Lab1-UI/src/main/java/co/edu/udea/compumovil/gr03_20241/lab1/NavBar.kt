package co.edu.udea.compumovil.gr03_20241.lab1

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("ResourceAsColor")
@Composable
fun NavBar(
    route: String,
    items: List<DestinationItem>
) {
    Orientation(
        landscape = {
            NavigationRail(
                modifier = Modifier
                    .shadow(elevation = 4.dp),
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                items.forEach { item ->
                    NavigationRailItem(
                        icon = {
                            Icon(
                                if (route == item.key) item.selectedIcon else item.unselectedIcon,
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = { Text(stringResource(id = item.title)) },
                        selected = route == item.key,
                        onClick = { item.functions.invoke() }
                    )
                }
            }
        },
        portrait = {
            NavigationBar(
                modifier = Modifier
                    .shadow(elevation = 4.dp),
                containerColor = MaterialTheme.colorScheme.background,
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (route == item.key) item.selectedIcon else item.unselectedIcon,
                                contentDescription = stringResource(id = item.title)
                            )
                        },
                        label = { Text(stringResource(id = item.title)) },
                        selected = route == item.key,
                        onClick = { item.functions.invoke() }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun NavBarPreview( navController: NavHostController = rememberNavController(),) {
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    val routes = getDestinations(navigationActions)
    NavBar("Home", routes);
}