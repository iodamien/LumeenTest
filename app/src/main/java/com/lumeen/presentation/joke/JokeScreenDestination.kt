package com.lumeen.presentation.joke

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.lumeen.R

enum class JokeScreenDestination(val route: String, @StringRes val titleResId: Int) {
    RandomJoke("RandomJoke", titleResId = R.string.random_joke),
    Favorite("Favorite", titleResId = R.string.favorite)
}

@Composable
internal fun NavHostController.collectJokeScreenDestination(): State<JokeScreenDestination?> {
    val navBackStackEntry by currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    return remember(currentRoute) {
        derivedStateOf {
            JokeScreenDestination.entries.firstOrNull { it.route == currentRoute }
        }
    }
}
