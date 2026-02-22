package edu.ucne.registro_api.data.remote.api

import edu.ucne.registro_api.data.remote.dto.PlanetDto
import edu.ucne.registro_api.data.remote.dto.PlanetResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DragonBallApi {
    @GET("planets")
    suspend fun getPlanets(): Response<PlanetResponseDto>

    @GET("planets/{id}")
    suspend fun getPlanetDetail(@Path("id") id: Int): Response<PlanetDto>
}