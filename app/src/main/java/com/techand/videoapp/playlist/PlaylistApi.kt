package com.techand.videoapp.playlist

import retrofit2.http.GET

interface PlaylistApi {

    @GET("playlists")
    suspend fun fetchPlayList(): List<PlaylistRaw>

}
