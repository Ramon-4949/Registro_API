package edu.ucne.registro_api.data.repository

import edu.ucne.registro_api.data.remote.api.DragonBallApi
import edu.ucne.registro_api.domain.planets_api.model.Planet
import edu.ucne.registro_api.domain.repository.PlanetRepository
import edu.ucne.registro_api.domain.utils.Resource
import javax.inject.Inject

class PlanetRepositoryImpl @Inject constructor(
    private val api: DragonBallApi
) : PlanetRepository {

    override suspend fun getPlanets(): Resource<List<Planet>> {
        return try {
            val response = api.getPlanets()
            if (response.isSuccessful && response.body() != null) {
                val data = response.body()!!.items.map { it.toDomain() }
                Resource.Success(data)
            } else {
                Resource.Error("Error servidor: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Error conexión: ${e.localizedMessage}")
        }
    }

    override suspend fun getPlanetDetail(id: Int): Resource<Planet> {
        return try {
            val response = api.getPlanetDetail(id)
            if (response.isSuccessful && response.body() != null) {
                Resource.Success(response.body()!!.toDomain())
            } else {
                Resource.Error("Planeta no encontrado")
            }
        } catch (e: Exception) {
            Resource.Error("Error: ${e.message}")
        }
    }
}