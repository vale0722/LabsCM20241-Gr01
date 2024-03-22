package co.edu.udea.compumovil.gr03_20241.lab1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineMedium,
        )

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
        ) {
            Text(
                text = stringResource(id = R.string.general_objective),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(800)
            )
            Text(
                text = stringResource(id = R.string.general_objective_description),
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Start,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledContentColor = MaterialTheme.colorScheme.onBackground,
            ),
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.members),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(800)
                )
                Text(
                    text = "- Valeria Granada Rodas",
                    modifier = Modifier
                        .padding(
                            vertical = 5.dp
                        ),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "- Alejandro Castrillon Ciro",
                    modifier = Modifier
                        .padding(
                            vertical = 5.dp
                        ),
                    textAlign = TextAlign.Start,
                )
                Text(
                    text = "- Daniela Vasquez",
                    modifier = Modifier.padding(vertical = 5.dp),
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}