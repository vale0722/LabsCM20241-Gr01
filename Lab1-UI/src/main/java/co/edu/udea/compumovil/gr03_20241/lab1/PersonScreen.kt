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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

data class Personal(
    val name: String,
    val gender: String?,
    val birthdate: String,
    val grade: String?
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun PersonScreen() {
    val (name, onNameChange) = rememberSaveable { mutableStateOf("") }
    val (isNameError, onNameError) = rememberSaveable { mutableStateOf(false) }
    val (lastname, onLastnameChange) = rememberSaveable { mutableStateOf("") }
    val (isLastNameError, onLastNameError) = rememberSaveable { mutableStateOf(false) }
    val genders = listOf(stringResource(id = R.string.male), stringResource(id = R.string.female))
    val (gender, onGender) = remember { mutableStateOf(genders[0]) }
    val (selectedDate, onSelectedDate) = remember { mutableStateOf("") }
    val (isDateError, onDateError) = rememberSaveable { mutableStateOf(false) }
    val grades = listOf(
        stringResource(id = R.string.primary),
        stringResource(id = R.string.secondary),
        stringResource(id = R.string.university),
        stringResource(id = R.string.other)
    )
    val (grade, onGradeChange) = remember { mutableStateOf(grades[0]) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val errorMessage = stringResource(id = R.string.error_person);
    val successMessage = stringResource(id = R.string.success);

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                            label = stringResource(id = R.string.name),
                            placeholder = stringResource(id = R.string.name_placeholder),
                            field = name,
                            onFieldChange = onNameChange,
                            error = isNameError
                        )
                        Spacer(modifier = Modifier.padding(26.dp))
                        GetInputTextField(
                            label = stringResource(id = R.string.lastname),
                            placeholder = stringResource(id = R.string.lastname_placeholder),
                            field = lastname,
                            onFieldChange = onLastnameChange,
                            error = isLastNameError
                        )
                    }

                    Spacer(modifier = Modifier.padding(4.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            GetGenderField(genders, gender, onGender)
                        }
                    }
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetCalendar(
                        selectedDate = selectedDate,
                        onSelectedDate = onSelectedDate,
                        error = isDateError
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    GetGradeField(grades, grade, onGradeChange)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .height(56.dp),
                        onClick = {
                            ValidateForm(
                                name = name,
                                lastname = lastname,
                                gender = gender,
                                birthdate = selectedDate,
                                grade = grade,
                                errorMessage = errorMessage,
                                snackbarHostState = snackbarHostState,
                                scope = scope,
                                successMessage = successMessage,
                                nameError = onNameError,
                                lastnameError = onLastNameError,
                                dateError = onDateError,
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
                    label = stringResource(id = R.string.name),
                    placeholder = stringResource(id = R.string.name_placeholder),
                    field = name,
                    onFieldChange = onNameChange,
                    error = isNameError
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetInputTextField(
                    label = stringResource(id = R.string.lastname),
                    placeholder = stringResource(id = R.string.lastname_placeholder),
                    field = lastname,
                    onFieldChange = onLastnameChange,
                    error = isLastNameError
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetGenderField(genders, gender, onGender)
                Spacer(modifier = Modifier.padding(4.dp))
                GetCalendar(
                    selectedDate = selectedDate,
                    onSelectedDate = onSelectedDate,
                    error = isDateError
                )
                Spacer(modifier = Modifier.padding(4.dp))
                GetGradeField(grades, grade, onGradeChange)
                Spacer(modifier = Modifier.padding(4.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(56.dp),
                    onClick = {
                        ValidateForm(
                            name = name,
                            lastname = lastname,
                            gender = gender,
                            birthdate = selectedDate,
                            grade = grade,
                            errorMessage = errorMessage,
                            scope = scope,
                            snackbarHostState = snackbarHostState,
                            successMessage = successMessage,
                            nameError = onNameError,
                            lastnameError = onLastNameError,
                            dateError = onDateError,
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
        })
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
fun ValidateForm(
    name: String,
    lastname: String,
    gender: String,
    birthdate: String,
    grade: String,
    errorMessage: String,
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    successMessage: String,
    nameError: (Boolean) -> Unit,
    lastnameError: (Boolean) -> Unit,
    dateError: (Boolean) -> Unit
) {
    var hasError = false
    if(name.isEmpty()) {
        hasError = true
        nameError(true)
    }
    if(lastname.isEmpty()) {
        hasError = true
        lastnameError(true)
    }
    if(birthdate.isEmpty()) {
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
        logInfo(Personal(
            name = "$name $lastname",
            gender = gender,
            birthdate = birthdate,
            grade = grade,
        ))
    }
}

fun logInfo(data: Personal) {
    Log.d("PERSONAL_INFORMATION", StringBuilder().apply {
        append("-------------------------------------\n")
        append("Información personal:\n")
        append("${data.name}\n")
        if (data.gender != null) {
            append("${data.gender}\t\t\n")
        }
        append("Nació el ${data.birthdate}\n")
        if (data.grade != null) {
            append("${data.grade}\t\t\n")
        }
        append("-------------------------------------\n")
    }.toString())
}

@Composable
fun GetGenderField(genders: List<String>, gender: String, onGender: (String) -> Unit) {
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
                        selected = (text == gender),
                        onClick = { onGender(text) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (text == gender),
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
fun GetCalendar(selectedDate: String, onSelectedDate: (String) -> Unit, error: Boolean) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance();
    OutlinedTextField(
        isError = error,
        value = selectedDate,
        onValueChange = { },
        label = {
            Text(stringResource(id = R.string.birthdate) + '*')
        },
        placeholder = {
            Text(stringResource(id = R.string.birthdate_placeholder))
        },
        enabled = false,
        trailingIcon = {
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
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetGradeField(grades: List<String>, grade: String, onGradeChange: (String) -> Unit) {
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
                value = grade,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
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
fun GetInputTextField(label: String, placeholder: String, field: String, onFieldChange: (String) -> Unit, error: Boolean)
{
    OutlinedTextField(
        modifier = Modifier,
        value = field,
        onValueChange = {
            onFieldChange(it)
        },
        label = {
            Text("$label*")
        },
        placeholder = {
            Text(placeholder)
        },
        keyboardOptions = KeyboardOptions(
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