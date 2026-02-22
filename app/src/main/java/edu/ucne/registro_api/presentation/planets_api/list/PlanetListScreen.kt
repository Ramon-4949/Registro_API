package edu.ucne.registro_api.presentation.planets_api.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import edu.ucne.registro_api.domain.planets_api.model.Planet

@Composable
fun PlanetListScreen(
    viewModel: PlanetViewModel = hiltViewModel(),
    onPlanetClick: (Int) -> Unit
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    PlanetListBody(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onPlanetClick = onPlanetClick
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetListBody(
    uiState: PlanetUiState,
    onEvent: (PlanetUiEvent) -> Unit,
    onPlanetClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Planetas Dragon Ball",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = uiState.filterName,
                onValueChange = { onEvent(PlanetUiEvent.UpdateFilter(it)) },
                label = { Text("Buscar por nombre, ID o estado") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.filteredPlanets.isEmpty() && uiState.error == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No se encontraron planetas",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = uiState.error,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.filteredPlanets) { planet ->
                        PlanetCard(
                            planet = planet,
                            onClick = { onPlanetClick(planet.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetCard(
    planet: Planet,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = planet.image,
                contentDescription = "Imagen de ${planet.name}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = planet.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )

                    if (planet.isDestroyed) {
                        Badge(containerColor = MaterialTheme.colorScheme.error) {
                            Text("Destruido", modifier = Modifier.padding(4.dp), color = Color.White)
                        }
                    } else {
                        Badge(containerColor = Color(0xFF4CAF50)) {
                            Text("Intacto", modifier = Modifier.padding(4.dp), color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = planet.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PlanetListPreview() {
    MaterialTheme {
        PlanetListBody(
            uiState = PlanetUiState(
                isLoading = false,
                filteredPlanets = listOf(
                    Planet(
                        id = 1,
                        name = "Namek",
                        isDestroyed = true,
                        description = "Planeta natal de los Namekianos. Escenario de importantes batallas.",
                        image = "https://dragonball-api.com/planetas/Namek_U7.webp"
                    ),
                    Planet(
                        id = 2,
                        name = "Tierra",
                        isDestroyed = false,
                        description = "El planeta principal donde se desarrolla la serie.",
                        image = "https://dragonball-api.com/planetas/Tierra_Dragon_Ball_Z.webp"
                    )
                )
            ),
            onEvent = {},
            onPlanetClick = {}
        )
    }
}