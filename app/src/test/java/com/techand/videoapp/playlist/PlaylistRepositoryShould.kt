package com.techand.videoapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PlaylistRepositoryShould : BaseUnitTest() {

    private val service: PlayListService = mock()
    private val repository = PlayListRepository(service)
    private val playlists = mock<List<Playlist>>()
    private val exception =RuntimeException("Error")


    @Test
    fun getPlaylistFromService() = runBlockingTest {
        repository.getPlaylist()
        verify(service, times(1)).fetchPlayList()
    }


    @Test
    fun emitPlaylistFromService() = runBlockingTest {
        whenever(service.fetchPlayList()).thenReturn(
            flow {
                emit(Result.success(playlists))
            })
        assertEquals(playlists, repository.getPlaylist().first().getOrNull())
    }

    @Test
    fun emitErrorFromService()= runBlockingTest {
        whenever(service.fetchPlayList()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )
        assertEquals(exception,repository.getPlaylist().first().exceptionOrNull())
    }
}