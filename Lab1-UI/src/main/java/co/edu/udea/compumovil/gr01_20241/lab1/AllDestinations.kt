package co.edu.udea.compumovil.gr01_20241.lab1

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

enum class DestinationItem(
    @StringRes val title: Int,
    val key: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var functions: () -> Unit
) {
    HOME(
        title = R.string.home,
        key = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        functions = { }
    ),
    PERSON(
        title = R.string.person,
        key = "Person",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
        functions = { }
    ),
    CONTACT(
        title = R.string.contact,
        key = "Contact",
        selectedIcon = Icons.Filled.Call,
        unselectedIcon = Icons.Outlined.Call,
        functions = { }
    );

    companion object {
        fun fromName(name: String): DestinationItem? =
            values().find { it.name.equals(name, ignoreCase = true) }
    }
}

class AppNavigationActions(private val navController: NavHostController) {
    fun navigateToHome() {
        navController.navigate(DestinationItem.HOME.key) {
            popUpTo(DestinationItem.HOME.key)
        }
    }

    fun navigateToPerson() {
        navController.navigate(DestinationItem.PERSON.key) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToContact() {
        navController.navigate(DestinationItem.CONTACT.key) {
            launchSingleTop = true
            restoreState = true
        }
    }
}

fun getDestinations(actions: AppNavigationActions): List<DestinationItem> {
    DestinationItem.HOME.functions = { actions.navigateToHome() }
    DestinationItem.PERSON.functions = { actions.navigateToPerson() }
    DestinationItem.CONTACT.functions = { actions.navigateToContact() }

    return enumValues<DestinationItem>().toList();
}