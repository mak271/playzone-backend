package ru.playzone.features.games.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateGameRequest(
    val title: String,
    val description: String,
    val version: String,
    val size: String
)

@Serializable
data class CreateGameResponse(
    val gameId: String,
    val title: String,
    val description: String,
    val version: String,
    val size: String
)