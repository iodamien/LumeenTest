package com.lumeen.presentation.joke.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lumeen.domain.model.Joke
import com.lumeen.domain.usecase.DeleteFavoriteJoke
import com.lumeen.domain.usecase.GetFavoriteJokeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoriteJokeViewModel @Inject constructor(
    private val getFavoriteJokeList: GetFavoriteJokeList,
    private val deleteFavoriteJoke: DeleteFavoriteJoke,
): ViewModel() {

    private val _favoriteJokeList = MutableStateFlow<List<Joke>>(emptyList())
    val favoriteJokeList: Flow<List<Joke>> = _favoriteJokeList.asStateFlow()

    init {
        viewModelScope.launch {
            getFavoriteJokeList()
                .collectLatest { jokeList -> _favoriteJokeList.value = jokeList }
        }
    }

    fun deleteJoke(jokeId: Long) {
        viewModelScope.launch {
            deleteFavoriteJoke(jokeId)
        }
    }
}
