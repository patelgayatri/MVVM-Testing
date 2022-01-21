package com.techand.videoapp.playlist

import kotlinx.coroutines.flow.Flow

class PlayListRepository(private val service: PlayListService) {

    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> = service.fetchPlayList()


}
