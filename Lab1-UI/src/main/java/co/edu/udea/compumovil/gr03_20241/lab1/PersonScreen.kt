package co.edu.udea.compumovil.gr03_20241.lab1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr03_20241.lab1.models.PersonalViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PersonScreen(personal: PersonalViewModel = viewModel(), navController: NavHostController = rememberNavController()) {
    val (isNameError, onNameError) = rememberSaveable { mutableStateOf(false) }
    val (isLastNameError, onLastNameError) = rememberSaveable { mutableStateOf(false) }
    val genders = listOf(stringResource(id = R.string.male), stringResource(id = R.string.female))
    val (isDateError, onDateError) = rememberSaveable { mutableStateOf(false) }
    val grades = listOf(
        stringResource(id = R.string.primary),
        stringResource(id = R.string.secondary),
        stringResource(id = R.string.university),
        stringResource(id = R.string.other)
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val errorMessage = stringResource(id = R.string.error_person);
    val successMessage = stringResource(id = R.string.success);

    val navigationActions = remember(navController) {
        AppNavigationActions(navController)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
                    .height(56.dp),
                onClick = {
                    ValidateForm(
                        personal = personal,
                        errorMessage = errorMessage,
                        scope = scope,
                        snackbarHostState = snackbarHostState,
                        successMessage = successMessage,
                        nameError = onNameError,
                        lastnameError = onLastNameError,
                        dateError = onDateError,
                        navigationActions = navigationActions
                    )
                },
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = stringResource(id = R.string.next),
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
                        text = stringResource(id = R.string.person),
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        GetInputTextField(
                            label = stringResource(id = R.string.name) + "*",
                            placeholder = stringResource(id = R.string.name_placeholder),
                            field = personal.name,
                            onFieldChange = { personal.setName(it) },
                            error = isNameError
                        )
                        Spacer(modifier = Modifier.padding(26.dp))
                        GetInputTextField(
                            label = stringResource(id = R.string.lastname) + "*",
                            placeholder = stringResource(id = R.string.lastname_placeholder),
                            field = personal.lastname,
                            onFieldChange = { personal.setLastname(it) },
                            error = isLastNameError
                        )
                    }

                    Spacer(modifier = Modifier.padding(4.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GetGenderField(genders, personal.gender, { personal.setGender(it) })
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetCalendar(
                        selectedDate = personal.birthdate,
                        onSelectedDate = { personal.setBirthdate(it) },
                        error = isDateError
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetGradeField(grades, personal.grade,{ personal.setGrade(it) })
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
                    text = stringResource(id = R.string.person),
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetInputTextField(
                    label = stringResource(id = R.string.name) + "*",
                    placeholder = stringResource(id = R.string.name_placeholder),
                    field = personal.name,
                    onFieldChange = { personal.setName(it) },
                    error = isNameError
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetInputTextField(
                    label = stringResource(id = R.string.lastname) + "*",
                    placeholder = stringResource(id = R.string.lastname_placeholder),
                    field = personal.lastname,
                    onFieldChange = { personal.setLastname(it) },
                    error = isLastNameError
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetGenderField(genders, personal.gender) { personal.setGender(it) }
                Spacer(modifier = Modifier.padding(4.dp))
                GetCalendar(
                    selectedDate = personal.birthdate,
                    onSelectedDate = { personal.setBirthdate(it) },
                    error = isDateError
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetGradeField(grades, personal.grade, { personal.setGrade(it) })
                Spacer(modifier = Modifier.padding(56.dp))
            }
        })
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
fun ValidateForm(
    personal: PersonalViewModel,
    errorMessage: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    successMessage: String,
    nameError: (Boolean) -> Unit,
    lastnameError: (Boolean) -> Unit,
    dateError: (Boolean) -> Unit,
    navigationActions: AppNavigationActions,
) {

    var hasError = false
    if(personal.name.value.isNullOrEmpty()) {
        hasError = true
        nameError(true)
    }
    if(personal.lastname.value.isNullOrEmpty()) {
        hasError = true
        lastnameError(true)
    }
    if(personal.birthdate.value.isNullOrEmpty()) {
        hasError = true
        dateError(true)
    }

    if(hasError) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = errorMessage
            )
        }
    } else {
        nameError(false);
        lastnameError(false);
        dateError(false);

        scope.launch {
            snackbarHostState.showSnackbar(
                message = successMessage
            )
        }
        navigationActions.navigateToContact()
    }
}

@Composable
fun GetGenderField(genders: List<String>, gender: State<String?>, onGender: (String) -> Unit) {
    Text(
        text = stringResource(id = R.string.gender),
        style = MaterialTheme.typography.bodyLarge
    )
    Spacer(modifier = Modifier.padding(10.dp))
    Row(Modifier.selectableGroup()) {
        genders.forEach { text ->
            Row(
                Modifier
                    .height(56.dp)
                    .selectable(
                        selected = (text == gender.value),
                        onClick = { onGender(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == gender.value),
                    onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun GetCalendar(selectedDate: State<String?>, onSelectedDate: (String) -> Unit, error: Boolean) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance();
    OutlinedTextField(
        isError = error,
        value = selectedDate.value.toString(),
        onValueChange = { },
        label = {
            Text(stringResource(id = R.string.birthdate) + '*')
        },
        placeholder = {
            Text(stringResource(id = R.string.birthdate_placeholder))
        },
        enabled = false,
        trailingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (error) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                Spacer(modifier = Modifier.padding(4.dp))
                IconButton(onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            onSelectedDate("$day/${month + 1}/$year")
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                    ).show()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetGradeField(grades: List<String>, grade: State<String?>, onGradeChange: (String) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.grade),
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
                value = grade.value.toString(),
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                placeholder = { Text(text = stringResource(id = R.string.grade_placeholder)) },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                grades.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            onGradeChange(selectionOption)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GetInputTextField(
    label: String,
    placeholder: String,
    field: State<String?>,
    onFieldChange: (String) -> Unit,
    error: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
)
{
    val fieldValue = field.value ?: ""

    OutlinedTextField(
        modifier = Modifier,
        value = fieldValue,
        onValueChange = {
            onFieldChange(it)
        },
        label = {
            Text(label)
        },
        placeholder = {
            Text(placeholder)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = KeyboardCapitalization.Sentences,
            autoCorrect = false
        ),
        trailingIcon = {
            if (error) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        isError = error,
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(backgroundColor = 0xFFFFFFFF, apiLevel = 33,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
fun PersonScreenPreview() {
    PersonScreen()
}