package co.edu.udea.compumovil.gr03_20241.lab1

import android.util.Log
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
import co.edu.udea.compumovil.gr03_20241.lab1.models.PersonalViewModel

enum class DestinationItem(
    @StringRes val title: Int,
    val key: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    var functions: () -> Unit,
    val show: Boolean = true
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
        functions = { },
        show = false
    ),
    SUCCESS(
        title = R.string.success,
        key = "Success",
        selectedIcon = Icons.Filled.Call,
        unselectedIcon = Icons.Outlined.Call,
        functions = { },
        show = false
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

    fun navigateToSuccess() {
        navController.navigate(DestinationItem.SUCCESS.key) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToContact() {
       try {
           navController.navigate(DestinationItem.CONTACT.key) {
               launchSingleTop = true
               restoreState = true
           }
       } catch (e: Exception) {
           Log.e("ERROR", e.message.toString())
       }
    }
}

fun getDestinations(actions: AppNavigationActions, personal: PersonalViewModel): List<DestinationItem> {
    DestinationItem.HOME.functions = { actions.navigateToHome() }
    DestinationItem.PERSON.functions = {
        if(personal.complete.value) {
            actions.navigateToSuccess()
        } else {
            actions.navigateToPerson()
        }
    }
    DestinationItem.CONTACT.functions = { actions.navigateToContact() }
    DestinationItem.SUCCESS.functions = { actions.navigateToSuccess() }

    return enumValues<DestinationItem>().toList();
}