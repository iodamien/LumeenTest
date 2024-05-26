package com.lumeen.presentation.joke

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lumeen.domain.model.Joke
import com.lumeen.presentation.joke.favorite.FavoriteJokeScreen
import com.lumeen.presentation.joke.favorite.FavoriteJokeViewModel
import com.lumeen.presentation.joke.random.RandomJokeScreen
import com.lumeen.presentation.joke.random.RandomJokeViewModel
import kotlinx.coroutines.launch

@Composable
internal fun JokeScreen(
    randomJokeViewModel: RandomJokeViewModel = hiltViewModel(),
    favoriteJokeViewModel: FavoriteJokeViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val canSaveAsFavorite: Boolean by randomJokeViewModel.canSaveAsFavorite.collectAsState(initial = false)
    val currentDestination: JokeScreenDestination? by navController.collectJokeScreenDestination()

    val showSaveFavoriteButton = canSaveAsFavorite && currentDestination == JokeScreenDestination.RandomJoke
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            JokeTopBar(navController)
        },
        bottomBar = {
            JokeBottomBar(navController)
        },
        floatingActionButton = {
            if (showSaveFavoriteButton) {
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            randomJokeViewModel.saveCurrentJokeAsFavorite()
                            snackbarHostState.showSnackbar("Saved as favorite")
                        }
                    },
                ) {
                    Icon(Icons.Filled.Star, "Save as favorite")
                }
            }
        }
    ) { padding ->
        NavHost(modifier = Modifier
            .padding(padding), navController = navController, startDestination = JokeScreenDestination.RandomJoke.route) {
            composable(JokeScreenDestination.RandomJoke.route) {
                val isLoading: Boolean by randomJokeViewModel.isLoading.collectAsState(initial = false)
                val errorMessage: String? by randomJokeViewModel.errorMessage.collectAsState(initial = null)
                val joke: Joke? by randomJokeViewModel.currentRandomJoke.collectAsState(initial = null)
                val isFavorite: Boolean by randomJokeViewModel.isFavorite.collectAsState(initial = false)

                RandomJokeScreen(
                    isLoading = isLoading,
                    joke = joke,
                    errorMessage = errorMessage,
                    isFavorite = isFavorite,
                    getNewJokeClicked = randomJokeViewModel::getRandomJoke,
                )
            }
            composable(JokeScreenDestination.Favorite.route) {
                val jokeList: List<Joke> by favoriteJokeViewModel.favoriteJokeList.collectAsState(initial = emptyList())

                FavoriteJokeScreen(
                    jokeList = jokeList,
                    onDeleteJoke = { jokeId ->
                        favoriteJokeViewModel.deleteJoke(jokeId)
                        scope.launch {
                            snackbarHostState.showSnackbar("Joked has been deleted")
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JokeTopBar(
    navController: NavHostController
) {
    val favoriteScreen: JokeScreenDestination? by navController.collectJokeScreenDestination()

    val localContext = LocalContext.current
    val title = favoriteScreen
        ?.titleResId
        ?.let { localContext.getString(it) }
        ?: ""


    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Red),
        title = { Text(title) },
    )
}

@Composable
private fun JokeBottomBar(
    navController: NavHostController,
) {

    val context = LocalContext.current
    val favoriteScreen: JokeScreenDestination? by navController.collectJokeScreenDestination()

    NavigationBar(
        containerColor = Color(0xFF2980b9),
    ) {
        NavigationBarItem(
            modifier = Modifier
                .alpha(
                    if (favoriteScreen == JokeScreenDestination.RandomJoke) 1f else 0.5f
                ),
            selected = favoriteScreen == JokeScreenDestination.RandomJoke,
            onClick = {
                navController.navigate(JokeScreenDestination.RandomJoke.route)
            },
            icon = {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            label = {
                Text(text = context.getString(JokeScreenDestination.RandomJoke.titleResId), color = Color.White)
            }
        )
        NavigationBarItem(
            modifier = Modifier
                .alpha(if (favoriteScreen == JokeScreenDestination.Favorite) 1f else 0.5f),
            selected = favoriteScreen == JokeScreenDestination.Favorite,
            onClick = {
                navController.navigate(JokeScreenDestination.Favorite.route)
            },
            icon = {
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            label = {
                Text(text = context.getString(JokeScreenDestination.Favorite.titleResId), color = Color.White)
            }
        )
    }
}

