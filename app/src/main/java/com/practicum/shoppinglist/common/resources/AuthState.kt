package com.practicum.shoppinglist.common.resources

data class AuthState(
    val status: Status,
) {

    enum class Status {
        DEFAULT,
        IN_PROGRESS,
        REGISTERED,
        ALREADY_EXISTS,
        BAD_REQUEST,
        ERROR
    }

    companion object {
        fun default() = AuthState(Status.DEFAULT)
    }
}