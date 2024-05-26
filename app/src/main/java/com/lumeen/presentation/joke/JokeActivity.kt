package com.lumeen.presentation.joke

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lueem.kit.designkit.theme.LumeenAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JokeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LumeenAppTheme {
                JokeScreen()
            }
        }
    }
}