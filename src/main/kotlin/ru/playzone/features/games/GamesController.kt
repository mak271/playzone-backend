package ru.playzone.features.games

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.playzone.database.games.Games
import ru.playzone.database.games.mapToCreateGameResponse
import ru.playzone.database.games.mapToGameDTO
import ru.playzone.database.games.mapToGameResponse
import ru.playzone.features.games.models.CreateGameRequest
import ru.playzone.features.games.models.FetchGamesRequest
import ru.playzone.features.games.models.FetchGamesResponse
import ru.playzone.utils.TokenCheck

class GamesController(private val call: ApplicationCall) {

    suspend fun performSearch() {
        val request = call.receive<FetchGamesRequest>()
        val token = call.request.headers["Bearer-Authorization"]

        if (TokenCheck.isTokenValid(token.orEmpty()) || TokenCheck.isTokenAdmin(token.orEmpty())) {
            call.respond(FetchGamesResponse(
                games = Games.fetchGames().filter {
                    it.title.contains(request.searchQuery, ignoreCase = true)
                } .map { it.mapToGameResponse() }
            ))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

    suspend fun createGame() {
        val token = call.request.headers["Bearer-Authorization"]
        if (TokenCheck.isTokenAdmin(token.orEmpty())) {
            val request = call.receive<CreateGameRequest>()
            val game = request.mapToGameDTO()
            Games.insert(game)
            call.respond(game.mapToCreateGameResponse())
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Token expired")
        }
    }

}