package ru.playzone.features.login

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.playzone.database.tokens.TokenDTO
import ru.playzone.database.tokens.Tokens
import ru.playzone.database.users.Users
import java.util.UUID

class LoginController(private val call: ApplicationCall) {

    suspend fun performLogin() {
        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        } else {
            if (userDTO.password == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        UUID.randomUUID().toString(),
                        receive.login,
                        token
                    )
                )
                call.respond(LoginResponseRemote(token))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }

}