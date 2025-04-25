package com.practicum.shoppinglist.core.domain.model

sealed interface Result<D, E> {
    class Success<D, E>(val data: D): Result<D, E>
    class Failure<D, E>(val error: E): Result<D, E>
}