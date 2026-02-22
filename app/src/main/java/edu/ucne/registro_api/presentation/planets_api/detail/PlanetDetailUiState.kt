package edu.ucne.registro_api.presentation.planets_api.detail


import edu.ucne.registro_api.domain.planets_api.model.Planet

data class PlanetDetailUiState(
    val isLoading: Boolean = false,
    val planet: Planet? = null,
    val error: String? = null
)