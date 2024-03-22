package co.edu.udea.compumovil.gr03_20241.lab1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.material.floatingactionbutton.FloatingActionButton

@Composable
fun PersonScreen() {
    var medicationName by rememberSaveable { mutableStateOf("") }
    var numberOfDosage by rememberSaveable { mutableStateOf("1") }

    Scaffold(

        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(56.dp),
                onClick = {},
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Text(
                    text = "next",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "nombre",
                style = MaterialTheme.typography.bodyLarge
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = medicationName,
                onValueChange = { medicationName = it },
                placeholder = {
                    Text(
                        text = "selecciona un nombre"
                    )
                },
            )

            Spacer(modifier = Modifier.padding(4.dp))

            var isMaxDoseError by rememberSaveable { mutableStateOf(false) }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val maxDose = 3

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "por dia",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    TextField(
                        modifier = Modifier.width(128.dp),
                        value = numberOfDosage,
                        onValueChange = {
                            if (it.length < maxDose) {
                                isMaxDoseError = false
                                numberOfDosage = it
                            } else {
                                isMaxDoseError = true
                            }
                        },
                        trailingIcon = {
                            if (isMaxDoseError) {
                                Icon(
                                    imageVector = Icons.Filled.Info,
                                    contentDescription = "ERROR",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = "wiii"
                            )
                        },
                        isError = isMaxDoseError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(4.dp))

            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = "no se que es",
                style = MaterialTheme.typography.bodyLarge
            )


            Button(
                onClick = {  }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                Text("Guardar")
            }
        }
    }
}

@Preview
@Composable
fun PersonScreenPreview() {
    PersonScreen()
}