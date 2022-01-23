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
    private val mapper: PlaylistMapper = mock()
    private val repository = PlayListRepository(service, mapper)
    private val playlists = mock<List<Playlist>>()
    private val playlistRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Error")


    @Test
    fun getPlaylistFromService() = runBlockingTest {
        repository.getPlaylist()
        verify(service, times(1)).fetchPlayList()
    }


    @Test
    fun emitMapperPlaylistFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        assertEquals(playlists, repository.getPlaylist().first().getOrNull())
    }


    @Test
    fun emitErrorFromService() = runBlockingTest {
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlaylist().first().exceptionOrNull())
    }


    @Test
    fun delegateBusinessLogicToMapper() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getPlaylist().first()
        verify(mapper, times(1)).invoke(playlistRaw)
    }

    private suspend fun mockSuccessfulCase(): PlayListRepository {
        whenever(service.fetchPlayList()).thenReturn(
            flow {
                emit(Result.success(playlistRaw))
            })
        whenever(mapper.invoke(playlistRaw)).thenReturn(playlists)
        return PlayListRepository(service, mapper)
    }

    private suspend fun mockFailureCase(): PlayListRepository {
        whenever(service.fetchPlayList()).thenReturn(
            flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            }
        )
        whenever(mapper.invoke(playlistRaw)).thenReturn(playlists)
        return PlayListRepository(service, mapper)
    }
}