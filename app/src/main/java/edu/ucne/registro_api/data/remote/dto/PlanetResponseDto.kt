package edu.ucne.registro_api.data.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlanetResponseDto(
    val items: List<PlanetDto>
)