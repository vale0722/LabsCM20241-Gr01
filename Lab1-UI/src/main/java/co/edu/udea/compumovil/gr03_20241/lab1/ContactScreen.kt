package co.edu.udea.compumovil.gr03_20241.lab1

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import androidx.compose.runtime.State
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr03_20241.lab1.models.Country
import co.edu.udea.compumovil.gr03_20241.lab1.models.Entity
import co.edu.udea.compumovil.gr03_20241.lab1.models.GeoNamesResponse
import co.edu.udea.compumovil.gr03_20241.lab1.models.PersonalViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ContactScreen(personal: PersonalViewModel = viewModel(),  navController: NavHostController = rememberNavController()) {
    val countries = mutableListOf<Entity>()
    val cities = mutableListOf<Entity>()
    val (isPhoneError, onPhoneError) = rememberSaveable { mutableStateOf(false) }
    val (isEmailError, onEmailError) = rememberSaveable { mutableStateOf(false) }
    val (isCountryError, onCountryError) = rememberSaveable { mutableStateOf(false) }
    fetchCountries(countries)
    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = stringResource(id = R.string.error_person);
    val successMessage = stringResource(id = R.string.success);
    val scope = rememberCoroutineScope()
    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar= {
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .height(56.dp),
                onClick = {
                    ValidateContactForm(
                        personal,
                        errorMessage,
                        snackbarHostState,
                        scope,
                        successMessage,
                        onPhoneError,
                        onEmailError,
                        onCountryError,
                        navigationActions
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) {
        Orientation(
            landscape = {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 34.dp, vertical = 16.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.contact),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        GetInputTextField(
                            label = stringResource(id = R.string.phone),
                            placeholder = stringResource(id = R.string.phone_placeholder),
                            field = personal.phone,
                            onFieldChange = { personal.setPhone(it) },
                            error = isPhoneError,
                            keyboardType = KeyboardType.Number
                        )
                        Spacer(modifier = Modifier.padding(26.dp))
                        GetInputTextField(
                            label = stringResource(id = R.string.email),
                            placeholder = stringResource(id = R.string.email_placeholder),
                            field = personal.email,
                            onFieldChange = { personal.setEmail(it) },
                            error = isEmailError
                        )
                    }

                    Spacer(modifier = Modifier.padding(4.dp))
                    GetSelectField(countries, personal.country, {
                        personal.setCountry(it)
                        personal.setCity(null)
                        if (it is Country) {
                            fetchCities(it.alpha2Code, cities);
                        } else {
                            cities.clear()
                        }
                    }, stringResource(id = R.string.country) + "*", isCountryError)
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetSelectField(cities, personal.city, {
                        personal.setCity(it)
                    }, stringResource(id = R.string.city))
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetInputTextField(
                        label = stringResource(id = R.string.address),
                        placeholder = stringResource(id = R.string.address_placeholder),
                        field = personal.address,
                        onFieldChange = { personal.setAddress(it) },
                        error = false
                    )
                    Spacer(modifier = Modifier.padding(56.dp))
                }
            },
            portrait = {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.contact),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetInputTextField(
                        label = stringResource(id = R.string.phone) + "*",
                        placeholder = stringResource(id = R.string.phone_placeholder),
                        field = personal.phone,
                        onFieldChange = { personal.setPhone(it) },
                        error = isPhoneError,
                        keyboardType = KeyboardType.Number
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetInputTextField(
                        label = stringResource(id = R.string.email) + "*",
                        placeholder = stringResource(id = R.string.email_placeholder),
                        field = personal.email,
                        onFieldChange = { personal.setEmail(it) },
                        error = isEmailError,
                        keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetSelectField(countries, personal.country, {
                        personal.setCountry(it)
                        personal.setCity(null)
                        if(it is Country) {
                            fetchCities(it.alpha2Code, cities);
                        } else {
                            cities.clear()
                        }
                    }, stringResource(id = R.string.country) + "*", isCountryError)
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetSelectField(cities, personal.city, {
                        personal.setCity(it)
                    }, stringResource(id = R.string.city))
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetInputTextField(
                        label = stringResource(id = R.string.address),
                        placeholder = stringResource(id = R.string.address_placeholder),
                        field = personal.address,
                        onFieldChange = { personal.setAddress(it) },
                        error = false
                    )
                    Spacer(modifier = Modifier.padding(56.dp))
                }
            }
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
fun ValidateContactForm(
    personal: PersonalViewModel,
    errorMessage: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    successMessage: String,
    phoneError: (Boolean) -> Unit,
    emailError: (Boolean) -> Unit,
    countryError: (Boolean) -> Unit,
    navigationActions: AppNavigationActions
) {
    var hasError = false
    if(personal.phone.value.isNullOrEmpty()) {
        hasError = true
        phoneError(true)
    }
    if(personal.email.value.isNullOrEmpty()) {
        hasError = true
        emailError(true)
    }
    if(personal.country.value.isNullOrEmpty()) {
        hasError = true
        countryError(true)
    }

    if(hasError) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = errorMessage
            )
        }
    } else {
        phoneError(false);
        emailError(false);
        countryError(false);

        scope.launch {
            snackbarHostState.showSnackbar(
                message = successMessage
            )
        }

        logInfo(personal)
        personal.setComplete(true);
        navigationActions.navigateToSuccess()
    }
}

fun logInfo(data: PersonalViewModel) {
    Log.d("PERSONAL_INFORMATION", StringBuilder().apply {
        append("-------------------------------------\n")
        append("Información personal:\n")
        append("${data.name.value} ${data.lastname.value}\n")
        if (data.gender.value != null) {
            append("${data.gender.value}\t\t\n")
        }
        append("Nació el ${data.birthdate.value}\n")
        if (data.grade.value != null) {
            append("${data.grade.value}\t\t\n")
        }
        append("-------------------------------------\n")
    }.toString())

    Log.d("CONTACT_INFORMATION", StringBuilder().apply {
        append("-------------------------------------\n")
        append("Información de contacto:\n")
        append("Teléfono: ${data.phone.value}\n")
        if (data.address.value != null) {
            append("Dirección: ${data.address.value}\n")
        }
        append("Correo Electrónico: ${data.email.value}\n")
        append("País: ${data.country.value?.name}\n")
        if (data.city.value.isNullOrEmpty()) {
            append("Ciudad: ${data.city.value?.name}\n")
        }
        append("-------------------------------------\n")
    }.toString())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetSelectField(
    entities: MutableList<Entity>,
    entity: State<Entity?>,
    onEntityChange: (Entity?) -> Unit,
    label: String,
    isCountryError: Boolean = false
) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge
            )

            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    modifier = Modifier.menuAnchor(),
                    readOnly = true,
                    isError = isCountryError == true,
                    value = entity.value?.name ?: "",
                    onValueChange = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    entities.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption.name) },
                            onClick = {
                                onEntityChange(selectionOption)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
}

fun fetchCities(alpha2Code: String?, cities: MutableList<Entity>) {
    if (alpha2Code != null) {
        citiesService.getCities(alpha2Code, "valeria.granada1").enqueue(object : Callback<GeoNamesResponse> {
            override fun onResponse(call: Call<GeoNamesResponse>, response: Response<GeoNamesResponse>) {
                if (response.isSuccessful) {
                    cities.clear();
                    cities.addAll(response.body()?.geonames ?: emptyList());
                }
            }

            override fun onFailure(call: Call<GeoNamesResponse>, t: Throwable) {
                Log.e("CITY", t.message.toString())
                cities.clear();
            }
        })
    }
}

fun fetchCountries(countries: MutableList<Entity>)
{
    countriesService.getCountries().enqueue(object : Callback<List<Country>> {
        override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
            Log.e("COUNTRY", response.message())
            if (response.isSuccessful) {
                countries.clear();
                countries.addAll(response.body() ?: emptyList());
            }
        }

        override fun onFailure(call: Call<List<Country>>, t: Throwable) {
            Log.e("COUNTRY_SERVICE", t.message.toString())
        }
    })
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(backgroundColor = 0xFFFFFFFF, apiLevel = 33)
@Composable
fun ContactScreenPreview() {
    ContactScreen()
}