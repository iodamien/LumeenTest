package com.lumeen.presentation.joke.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lumeen.R
import com.lumeen.domain.model.Joke
import com.lumeen.presentation.joke.JokeSection

@Composable
internal fun JokeRow(joke: Joke, onDeleteClicked: () -> Unit) {
    val localContext = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        JokeSection(modifier = Modifier.weight(1f), joke = joke, isFavorite = true)
        Icon(
            modifier = Modifier
                .clickable {
                    onDeleteClicked()
                },
            painter = painterResource(id = com.lumeen.kit.designkit.R.drawable.ic_trash_24),
            contentDescription = localContext.getString(com.lumeen.R.string.delete_joke)
        )
    }
}

@Composable
internal fun FavoriteJokeScreen(
    jokeList: List<Joke>,
    onDeleteJoke: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        items(jokeList) { joke ->
            JokeRow(joke) { onDeleteJoke(joke.id) }
        }
    }
}
