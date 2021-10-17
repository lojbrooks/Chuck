package com.lojbrooks.chuck.presentation.jokelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lojbrooks.chuck.domain.usecase.GetJokesQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JokeListViewModel @Inject constructor(private val getJokesQuery: GetJokesQuery) : ViewModel() {

    init {
        viewModelScope.launch {
            getJokesQuery()
        }
    }
}