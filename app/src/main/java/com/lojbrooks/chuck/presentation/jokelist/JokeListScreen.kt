package com.lojbrooks.chuck.presentation.jokelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.text.parseAsHtml
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lojbrooks.chuck.R
import com.lojbrooks.chuck.domain.model.Joke

@Composable
fun JokeListScreen(viewModel: JokeListViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        JokeListViewModel.State.Loading -> LoadingIndicator()
        JokeListViewModel.State.Error -> JokeListError(viewModel::onTryAgainClicked)
        is JokeListViewModel.State.Data -> JokeList(currentState.jokes)
    }
}

@Composable
fun JokeList(jokes: List<Joke>) {
    LazyColumn(modifier = Modifier.padding(vertical = 8.dp)) {
        items(jokes) { joke ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = joke.text.parseAsHtml().toString(), modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun JokeListError(onTryAgainClicked: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(id = R.string.joke_list_error))
        TextButton(onClick = onTryAgainClicked) {
            Text(text = stringResource(id = R.string.try_again_button))
        }
    }
}