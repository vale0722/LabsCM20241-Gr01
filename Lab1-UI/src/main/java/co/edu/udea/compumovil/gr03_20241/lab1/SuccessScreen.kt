package co.edu.udea.compumovil.gr03_20241.lab1

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.State
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr03_20241.lab1.models.City
import co.edu.udea.compumovil.gr03_20241.lab1.models.Country
import co.edu.udea.compumovil.gr03_20241.lab1.models.Entity
import co.edu.udea.compumovil.gr03_20241.lab1.models.GeoNamesResponse
import co.edu.udea.compumovil.gr03_20241.lab1.models.PersonalViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SuccessScreen(personal: PersonalViewModel = viewModel(),  navController: NavHostController = rememberNavController()) {
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 34.dp, vertical = 16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 34.dp, vertical = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.success),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(24.dp))
            Image(
                painterResource(R.drawable.success),
                contentDescription = stringResource(id = R.string.success)
            )
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 34.dp, vertical = 16.dp)
                .fillMaxWidth(),

            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.personal_info),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.5F),
                        text = stringResource(id = R.string.name),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier =Modifier.fillMaxWidth(0.5F),
                        text = personal.name.value + " " +  personal.lastname.value,
                    )
                }
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.born),
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = personal.birthdate.value + "",
                    )
                }
            }
            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                if (!personal.gender.value.isNullOrEmpty()) {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(0.5F),
                            text = stringResource(id = R.string.gender),
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(0.5F),
                            text = personal.gender.value + "",
                        )
                    }
                }
                if (!personal.grade.value.isNullOrEmpty()) {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.grade),
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = personal.grade.value + "",
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.contact_info),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.5F),
                        text = stringResource(id = R.string.phone),
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(0.5F),
                        text = personal.phone.value + "",
                    )
                }
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.email),
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = personal.email.value + "",
                    )
                }
            }
            Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(0.5F),
                        text = stringResource(id = R.string.country),
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(0.5F),
                        text = personal.country.value?.name + "",
                    )
                }
                if(!personal.city.value.isNullOrEmpty()) {
                    Column {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(id = R.string.city),
                            fontWeight = FontWeight.Bold,
                        )

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = personal.city.value?.name + "",
                        )
                    }
                }
            }
            if(!personal.address.value.isNullOrEmpty()) {
                Column {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.address),
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = personal.address.value + "",
                    )
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .height(56.dp),
                onClick = {
                    personal.clear()
                    navigationActions.navigateToPerson();
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.clear),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(backgroundColor = 0xFFFFFFFF, apiLevel = 33,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
fun SuccessScreenPreview(personal: PersonalViewModel = viewModel()) {
    personal.setName("Pepito")
    personal.setLastname("Perez")
    personal.setBirthdate("2024/01/01")
    personal.setGender("Masculino")
    personal.setGrade("Universidad")
    personal.setAddress("calle 20")
    personal.setCountry(Country("Colombia", "CO"))
    personal.setCity(City("Medellín"))
    personal.setPhone("3128900123")
    personal.setEmail("pepito@mail.com")
    SuccessScreen(personal)
}