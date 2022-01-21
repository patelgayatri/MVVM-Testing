package com.techand.videoapp.playlist

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayListRepository @Inject constructor(private val service: PlayListService) {

    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> = service.fetchPlayList()


}
