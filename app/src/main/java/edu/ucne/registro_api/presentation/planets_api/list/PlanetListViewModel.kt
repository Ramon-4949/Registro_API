package edu.ucne.registro_api.presentation.planets_api.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_api.domain.usecase.GetPlanetsUseCase
import edu.ucne.registro_api.domain.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val getPlanetsUseCase: GetPlanetsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PlanetUiState())
    val state = _state.asStateFlow()

    init {
        loadPlanets()
    }

    fun onEvent(event: PlanetUiEvent) {
        when (event) {
            is PlanetUiEvent.UpdateFilter -> {
                _state.update { it.copy(filterName = event.name) }
                val query = event.name.trim()
                val filtered = if (query.isBlank()) {
                    _state.value.allPlanets
                } else {
                    _state.value.allPlanets.filter { planet ->
                        val coincideNombre = planet.name.contains(query, ignoreCase = true)
                        val coincideId = planet.id.toString().contains(query)
                        val estadoTexto = if (planet.isDestroyed) "destruido" else "intacto"
                        val coincideEstado = estadoTexto.contains(query, ignoreCase = true)
                        coincideNombre || coincideId || coincideEstado
                    }
                }
                _state.update { it.copy(filteredPlanets = filtered) }
            }
            PlanetUiEvent.Search -> loadPlanets()
        }
    }
    private fun loadPlanets() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (val result = getPlanetsUseCase()) {
                is Resource.Success -> {
                    val planets = result.data ?: emptyList()
                    _state.update {
                        it.copy(
                            isLoading = false,
                            allPlanets = planets,
                            filteredPlanets = planets
                        )
                    }
                }
                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
                is Resource.Loading -> _state.update { it.copy(isLoading = true) }
            }
        }
    }
}