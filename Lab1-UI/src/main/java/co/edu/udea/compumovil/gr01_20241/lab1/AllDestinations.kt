package co.edu.udea.compumovil.gr01_20241.lab1

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.navigation.NavHostController
import co.edu.udea.compumovil.gr01_20241.lab1.AllDestinations.CONTACT
import co.edu.udea.compumovil.gr01_20241.lab1.AllDestinations.HOME
import co.edu.udea.compumovil.gr01_20241.lab1.AllDestinations.PERSON

object AllDestinations {
    const val HOME = "Home"
    const val PERSON = "Person"
    const val CONTACT = "Contact"
}

class AppNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(HOME) {
            popUpTo(HOME)
        }
    }

    fun navigateToPerson() {
        navController.navigate(PERSON) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToContact() {
        navController.navigate(CONTACT) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun getDestinations(): List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                title = HOME,
                name = HOME,
                selectedIcon = Icons.Filled.Home,
                unselectedIcon = Icons.Outlined.Home,
                functions = { navigateToHome() }
            ),
            BottomNavigationItem(
                title = PERSON,
                name = PERSON,
                selectedIcon = Icons.Filled.Person,
                unselectedIcon = Icons.Outlined.Person,
                functions = { navigateToPerson() }
            ),
            BottomNavigationItem(
                title = CONTACT,
                name = CONTACT,
                selectedIcon = Icons.Filled.Call,
                unselectedIcon = Icons.Outlined.Call,
                functions = { navigateToContact() }
            )
        )
    }
}