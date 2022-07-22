package ru.playzone.features.games

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureGamesRouting() {
    routing {
        post("/games/search") {
            val gamesController = GamesController(call)
            gamesController.performSearch()
        }

        post("/games/create") {
            val gamesController = GamesController(call)
            gamesController.createGame()
        }
    }
}