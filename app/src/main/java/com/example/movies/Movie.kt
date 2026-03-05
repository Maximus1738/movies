package com.example.movies

data class Movie(
    val title: String,
    val overview: String,
    val poster_path: String?
) {
    fun posterUrl(): String? {
        return poster_path?.let { Constants.IMAGE_BASE_URL + it.trimStart('/') }
    }
}
