package com.techand.videoapp.playlist

interface PlaylistApi {

    suspend fun fetchPlayList(): List<Playlist> {
        return listOf()
    }

}
