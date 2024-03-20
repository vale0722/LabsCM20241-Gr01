package co.edu.udea.compumovil.gr01_20241.lab1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItem(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.HOME
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = currentRoute) },
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        },
        modifier = Modifier,
        bottomBar = {
            AppDrawer(
                route = currentRoute,
                items = navigationActions.getDestinations()
            )
        },
    ) {
        Column {
            NavHost(
                navController = navController, startDestination = AllDestinations.HOME, modifier = modifier.padding(it)
            ) {

                composable(AllDestinations.HOME) {
                    HomeScreen()
                }

                composable(AllDestinations.PERSON) {
                    PersonScreen()
                }

                composable(AllDestinations.CONTACT) {
                    ContactScreen()
                }
            }
        }
    }
}