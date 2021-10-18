package com.lojbrooks.chuck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lojbrooks.chuck.presentation.jokelist.JokeListScreen
import com.lojbrooks.chuck.presentation.jokelist.JokeListViewModel
import com.lojbrooks.chuck.ui.theme.ChuckTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChuckTheme {
                Surface(color = MaterialTheme.colors.background) {
                    JokeListScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChuckTheme {
        Greeting("Android")
    }
}