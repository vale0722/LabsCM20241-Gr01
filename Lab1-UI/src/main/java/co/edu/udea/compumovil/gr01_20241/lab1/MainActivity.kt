package co.edu.udea.compumovil.gr01_20241.lab1

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import co.edu.udea.compumovil.gr01_20241.lab1.ui.theme.LabsCM20241Gr01Theme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

data class BottomNavigationItem(
    val title: String,
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val functions: () -> Unit,
)

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabsCM20241Gr01Theme {
                NavigationItem()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, apiLevel = 33)
@Composable
fun DefaultPreview() {
    LabsCM20241Gr01Theme {
        NavigationItem()
    }
}