package edu.ucne.registro_api.domain.repository

import edu.ucne.registro_api.domain.planets_api.model.Planet
import edu.ucne.registro_api.domain.utils.Resource


interface PlanetRepository {
    suspend fun getPlanets(): Resource<List<Planet>>
    suspend fun getPlanetDetail(id: Int): Resource<Planet>
}