package com.techand.videoapp.playlist

import com.techand.videoapp.R

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.mipmap.ic_launcher
)