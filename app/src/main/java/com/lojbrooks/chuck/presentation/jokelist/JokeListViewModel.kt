package com.lojbrooks.chuck.presentation.jokelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lojbrooks.chuck.domain.model.Joke
import com.lojbrooks.chuck.domain.usecase.GetJokesQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeListViewModel @Inject constructor(private val getJokesQuery: GetJokesQuery) : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        fetchJokeData()
    }

    private fun fetchJokeData() {
        viewModelScope.launch {
            _state.value = State.Loading
            when (val result = getJokesQuery()) {
                GetJokesQuery.Result.Error -> _state.value = State.Error
                is GetJokesQuery.Result.Success -> _state.value = State.Data(result.jokes)
            }
        }
    }

    fun onTryAgainClicked() {
        fetchJokeData()
    }

    sealed class State {
        object Loading: State()
        object Error: State()
        data class Data(val jokes: List<Joke>): State()
    }
}