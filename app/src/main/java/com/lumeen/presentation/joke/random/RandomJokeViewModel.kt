package com.lumeen.presentation.joke.random

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumeen.R
import com.lumeen.domain.model.Joke
import com.lumeen.domain.model.error.GetJokeError
import com.lumeen.domain.usecase.GetRandomJoke
import com.lumeen.domain.usecase.IsJokeFavorite
import com.lumeen.domain.usecase.SaveJokeAsFavorite
import com.lumeen.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RandomJokeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getJoke: GetRandomJoke,
    private val saveJokeAsFavorite: SaveJokeAsFavorite,
    private val isJokeFavorite: IsJokeFavorite,
): ViewModel() {

    private val _currentRandomJoke = MutableStateFlow<Joke?>(null)
    val currentRandomJoke: Flow<Joke?> = _currentRandomJoke.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: Flow<String?> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFavorite: Flow<Boolean> = currentRandomJoke.flatMapLatest { currentJoke ->
        if (currentJoke != null) {
            isJokeFavorite(currentJoke.id)
        } else {
            flowOf(false)
        }
    }

    val canSaveAsFavorite: Flow<Boolean> = combine(
        isFavorite,
        currentRandomJoke,
        isLoading,
    ) { isFavorite, currentJoke, isLoading -> (!isFavorite && currentJoke != null) || isLoading }

    fun getRandomJoke() {
        viewModelScope.launch {
            _isLoading.value = true
            when (val result = getJoke()) {
                is Result.Success -> {
                    _currentRandomJoke.value = result.value
                    _errorMessage.value = null
                }
                is Result.Error -> {
                    _errorMessage.value = prepareGetErrorMessage(error = result.value)
                }
            }
            _isLoading.value = false
        }
    }

    private fun prepareGetErrorMessage(error: GetJokeError): String {
        return when (error) {
            is GetJokeError.NetworkError -> context.getString(R.string.network_error)
            is GetJokeError.ClientError -> context.getString(R.string.error_client, error.code)
            is GetJokeError.ServerError -> context.getString(R.string.error_server, error.code)
            is GetJokeError.UnknownError -> context.getString(R.string.unknown_error, error.message.orEmpty())
        }
    }

    fun saveCurrentJokeAsFavorite() {
        viewModelScope.launch {
            currentRandomJoke.firstOrNull()?.also { currentJoke ->
                saveJokeAsFavorite(currentJoke)
            }
        }
    }
}
