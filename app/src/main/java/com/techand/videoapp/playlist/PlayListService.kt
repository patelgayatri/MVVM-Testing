package com.techand.videoapp.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayListService(val api: PlaylistApi) {
    suspend fun fetchPlayList(): Flow<Result<List<Playlist>>> {
        return flow {
            emit(Result.success(api.fetchPlayList()))
        }
    }

}
