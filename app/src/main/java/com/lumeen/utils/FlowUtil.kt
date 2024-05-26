package com.lumeen.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun<T, R> Flow<List<T>>.mapListNotNull(mapper: (T) -> R?): Flow<List<R>> {
    return map { list -> list.mapNotNull { item -> mapper(item) } }
}
