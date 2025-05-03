package com.practicum.shoppinglist.core.presentation.navigation

enum class Routes(val route: String) {
    SPLASH_SCREEN(SplashScreen::class.qualifiedName ?: ""),
    MAIN_SCREEN(MainScreen::class.qualifiedName ?: ""),
    DETAILS_SCREEN(DetailsScreen::class.qualifiedName ?: ""),
    REGISTRATION_SCREEN(RegistrationScreen::class.qualifiedName ?: ""),
    LOGIN_SCREEN(LoginScreen::class.qualifiedName ?: ""),
    RESTORE_PASSWORD_SCREEN(RestorePasswordScreen::class.qualifiedName ?: ""),
}