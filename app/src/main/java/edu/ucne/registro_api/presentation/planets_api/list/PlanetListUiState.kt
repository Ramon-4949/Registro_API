package edu.ucne.registro_api.presentation.planets_api.list

import edu.ucne.registro_api.domain.planets_api.model.Planet
data class PlanetUiState(
    val isLoading: Boolean = false,
    val allPlanets: List<Planet> = emptyList(),
    val filteredPlanets: List<Planet> = emptyList(),
    val error: String? = null,
    val filterName: String = ""
)