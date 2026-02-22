package edu.ucne.registro_api.domain.planets_api.model

data class Planet(
    val id : Int,
    val name : String,
    val isDestroyed : Boolean,
    val description : String,
    val image : String
)