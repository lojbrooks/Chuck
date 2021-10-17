package com.lojbrooks.chuck.domain.common

import javax.inject.Inject

class RandomNumberGenerator @Inject constructor() {
    fun getRandomInt(range: IntRange): Int {
        return range.random()
    }
}