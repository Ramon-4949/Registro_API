package edu.ucne.registro_api.presentation.planets_api.list
sealed interface PlanetUiEvent {
    data class UpdateFilter(val name: String) : PlanetUiEvent
    data object Search : PlanetUiEvent
}