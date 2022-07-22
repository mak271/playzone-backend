package ru.playzone.database.games

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Games: Table("games") {
    private val gameId = Games.varchar("gameId", 100)
    private val title = Games.varchar("title", 100)
    private val description = Games.varchar("description", 500)
    private val version = Games.varchar("version", 15)
    private val size = Games.varchar("size", 10)

    fun insert(gameDTO: GameDTO) {
        transaction {
            Games.insert {
                it[gameId] = gameDTO.gameId
                it[title] = gameDTO.title
                it[description] = gameDTO.description
                it[version] = gameDTO.version
                it[size] = gameDTO.size
            }
        }
    }

    fun fetchGames(): List<GameDTO> {
        return try {
            transaction {
                Games.selectAll().toList()
                    .map {
                        GameDTO(
                            gameId = it[gameId],
                            title = it[title],
                            description = it[description],
                            version = it[version],
                            size = it[size]
                        )
                    }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}