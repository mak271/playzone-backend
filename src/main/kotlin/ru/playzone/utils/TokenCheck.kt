package ru.playzone.utils

import ru.playzone.database.tokens.Tokens

object TokenCheck {
    fun isTokenValid(token: String): Boolean = Tokens.fetchTokens().firstOrNull { it.token == token } != null
    fun isTokenAdmin(token: String): Boolean = token == "15f4f713-7673-49ec-9c6c-536fbb874036"
}