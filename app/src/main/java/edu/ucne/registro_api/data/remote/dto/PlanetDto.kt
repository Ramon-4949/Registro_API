package edu.ucne.registro_api.data.remote.dto

import com.squareup.moshi.JsonClass
import edu.ucne.registro_api.domain.planets_api.model.Planet

@JsonClass(generateAdapter = true)
data class PlanetDto(
    val id: Int,
    val name: String,
    val isDestroyed: Boolean,
    val description: String,
    val image: String,
    val deletedAt: String?
) {
    fun toDomain() = Planet(
        id = id,
        name = name,
        isDestroyed = isDestroyed,
        description = description,
        image = image
    )
}