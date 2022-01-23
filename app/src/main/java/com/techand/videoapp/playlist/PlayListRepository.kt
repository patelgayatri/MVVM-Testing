package com.techand.videoapp.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayListRepository @Inject constructor(
    private val service: PlayListService,
    private val mapper: PlaylistMapper
) {

    suspend fun getPlaylist(): Flow<Result<List<Playlist>>> =
        service.fetchPlayList()
            .map {
                if (it.isSuccess)
                    Result.success(mapper(it.getOrNull()!!))
                else
                    Result.failure(it.exceptionOrNull()!!)
            }


}
