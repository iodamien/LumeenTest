package com.lumeen.presentation.joke

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.lumeen.R
import com.lumeen.domain.model.Joke

@Composable
private fun JokeSingle(joke: Joke.Single, isFavorite: Boolean) {
    JokeTextSection(
        text = joke.joke,
        backgroundColor = Color.Blue.copy(alpha = 0.2f),
        isFavorite = isFavorite,
    )
}

@Composable
private fun JokeTwoPart(joke: Joke.TwoParts, isFavorite: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        JokeTextSection(
            text = joke.setup,
            backgroundColor = Color.Blue.copy(alpha = 0.2f),
            isFavorite = isFavorite,
        )
        JokeTextSection(
            text = joke.delivery,
            backgroundColor = Color.Green.copy(alpha = 0.2f),
            isFavorite = false,
        )
    }
}

@Composable
internal fun JokeSection(modifier: Modifier, joke: Joke?, isFavorite: Boolean) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        when (joke) {
            is Joke.Single -> JokeSingle(joke = joke, isFavorite = isFavorite)
            is Joke.TwoParts -> JokeTwoPart(joke = joke, isFavorite = isFavorite)
            else -> Unit
        }
    }
}
@Composable
private fun JokeTextSection(text: String, backgroundColor: Color, isFavorite: Boolean) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (isFavorite) {
            Icon(
                imageVector = Icons.Filled.Star,
                tint = Color.Yellow,
                contentDescription = context.getString(R.string.favorite)
            )
        }
        Text(
            modifier = Modifier
                .weight(1f),
            text = text,
            textAlign = TextAlign.Center
        )
    }
}
