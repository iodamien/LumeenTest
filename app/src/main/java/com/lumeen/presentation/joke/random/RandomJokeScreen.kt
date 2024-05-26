package com.lumeen.presentation.joke.random

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lumeen.domain.model.Joke
import com.lumeen.presentation.joke.JokeSection

@Composable
internal fun RandomJokeScreen(
    isLoading: Boolean,
    errorMessage: String?,
    joke: Joke?,
    isFavorite: Boolean,
    getNewJokeClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        JokeSection(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            joke = joke,
            isFavorite = isFavorite
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Loading(isLoading = isLoading)
            ErrorMessage(message = errorMessage)
        }

        Button(onClick = getNewJokeClicked) {
            Text(text = "Get new Joke")
        }
    }
}

@Composable
private fun ErrorMessage(message: String?) {
    message?.also {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Red.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            text = message,
            color = Color.Red,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun Loading(isLoading: Boolean) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    } else {
        Box(modifier = Modifier.size(64.dp))
    }
}
