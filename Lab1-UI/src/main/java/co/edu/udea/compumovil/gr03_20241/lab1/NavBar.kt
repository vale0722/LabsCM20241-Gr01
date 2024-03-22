package co.edu.udea.compumovil.gr03_20241.lab1

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@SuppressLint("ResourceAsColor")
@Composable
fun NavBar(
    route: String,
    items: List<DestinationItem>
) {
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