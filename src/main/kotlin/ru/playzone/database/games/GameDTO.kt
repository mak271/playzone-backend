package ru.playzone.database.games

import ru.playzone.features.games.models.CreateGameRequest
import ru.playzone.features.games.models.CreateGameResponse
import ru.playzone.features.games.models.GameResponse
import java.util.UUID

data class GameDTO(
    val gameId: String,
    val title: String,
    val description: String,
    val version: String,
    val size: String
)

fun CreateGameRequest.mapToGameDTO(): GameDTO =
    GameDTO(
        gameId = UUID.randomUUID().toString(),
        title = title,
        description = description,
        version = version,
        size = size
    )

fun GameDTO.mapToCreateGameResponse(): CreateGameResponse =
    CreateGameResponse(
        gameId = gameId,
        title = title,
        description = description,
        version = version,
        size = size
    )

fun GameDTO.mapToGameResponse(): GameResponse =
    GameResponse(
        gameId = gameId,
        title = title,
        description = description,
        version = version,
        size = size
    )
