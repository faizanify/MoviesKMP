package com.habib.faizan.movieskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform