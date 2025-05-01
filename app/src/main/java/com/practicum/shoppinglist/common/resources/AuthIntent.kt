package com.practicum.shoppinglist.common.resources

sealed interface AuthIntent : BaseIntent {
    class Registration(val email: String, val password: String) : AuthIntent
    class Login(email: String, password: String) : AuthIntent
    class RestorePassword(email: String) : AuthIntent
}