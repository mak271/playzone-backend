package ru.playzone.features.register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.playzone.database.tokens.TokenDTO
import ru.playzone.database.tokens.Tokens
import ru.playzone.database.users.UserDTO
import ru.playzone.database.users.Users
import ru.playzone.utils.isValidEmail
import java.util.UUID

class RegisterController(private val call: ApplicationCall) {

    suspend fun registerNewUser() {

        val receive = call.receive<RegisterReceiveRemote>()
        if (!receive.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Bad Email")
        }

        val userDTO = Users.fetchUser(receive.login)

        if (userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        } else {
            val token = UUID.randomUUID().toString()

            try {
                Users.insert(
                    UserDTO(
                        login = receive.login,
                        password = receive.password,
                        email = receive.email,
                        username = ""
                    )
                )
            } catch (e: ExposedSQLException) {
                call.respond(HttpStatusCode.Conflict, "User already exists")

            }


            Tokens.insert(
                TokenDTO(
                    rowId = UUID.randomUUID().toString(), login = receive.login, token = token
                )
            )
            call.respond(RegisterResponseRemote(token = token))
        }

    }

}