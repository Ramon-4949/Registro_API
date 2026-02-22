package edu.ucne.registro_api.domain.usecase

import edu.ucne.registro_api.domain.repository.PlanetRepository
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    suspend operator fun invoke() = repository.getPlanets()
}