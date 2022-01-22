package com.techand.videoapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.verify
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest

@ExperimentalCoroutinesApi
class PlayListViewModelShould : BaseUnitTest() {

    private lateinit var viewmodel: PlayListViewModel
    private var repository: PlayListRepository = mock()
    private var playlists = mock<List<Playlist>>()
    private var expected = Result.success(playlists)
    private var exception = RuntimeException("Error")


    private suspend fun mockSuccessCase()= runBlockingTest {
        whenever(repository.getPlaylist()).thenReturn(
            flow {
                 emit(expected)
         })
        viewmodel = PlayListViewModel(repository)
    }
    private fun mockFailureCase() {
        runBlockingTest {
            whenever(repository.getPlaylist()).thenReturn(flow {
                emit(Result.failure<List<Playlist>>(exception))
            })
            viewmodel = PlayListViewModel(repository)
        }
    }
    @Test
    fun getDataFromRepository() = runBlockingTest {
        mockSuccessCase()
        viewmodel.playlists.getValueForTest()
        verify(repository, times(1)).getPlaylist()
    }

    @Test
    fun emitPlaylistsFromRepository() = runBlockingTest {
        mockSuccessCase()
        assertEquals(expected, viewmodel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        mockFailureCase()
        assertEquals(exception, viewmodel.playlists.getValueForTest()!!.exceptionOrNull())
    }

    @Test
    fun showSpinnerWhileLoading() = runBlockingTest {
        mockSuccessCase()
        viewmodel.loader.captureValues {
            viewmodel.playlists.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoadingAfterListLoaded()= runBlockingTest {
        mockSuccessCase()
        viewmodel.loader.captureValues {
            viewmodel.playlists.getValueForTest()
            assertEquals(false,values.last())
        }
    }
    @Test
    fun hideLoadingAfterFailure()= runBlockingTest {
        mockFailureCase()
        viewmodel.loader.captureValues {
            viewmodel.playlists.getValueForTest()
            assertEquals(false,values.last())
        }
    }
}