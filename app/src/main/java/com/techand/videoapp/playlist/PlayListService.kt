package com.techand.videoapp.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlayListService @Inject constructor(val api: PlaylistApi) {
    suspend fun fetchPlayList(): Flow<Result<List<PlaylistRaw>>> {
        return flow {
            emit(Result.success(api.fetchPlayList()))
        }.catch {
            emit(Result.failure(RuntimeException("Error")))
        }
    }

}
