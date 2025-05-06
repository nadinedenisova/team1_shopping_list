package com.practicum.shoppinglist.common.resources

sealed interface AuthIntent {
    class Registration(val email: String, val password: String) : AuthIntent
    class Login(val email: String, val password: String) : AuthIntent
    class RestorePassword(email: String) : AuthIntent
}