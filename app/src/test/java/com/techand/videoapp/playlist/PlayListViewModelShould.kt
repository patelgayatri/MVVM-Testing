package com.techand.videoapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import petros.efthymiou.groovy.utils.getValueForTest
@ExperimentalCoroutinesApi
class PlayListViewModelShould : BaseUnitTest() {

    private lateinit var viewmodel: PlayListViewModel
    private var repository: PlayListRepository = mock()
    private var playlists = mock<List<Playlist>>()
    private var expected = Result.success(playlists)
    private var exception = RuntimeException("Error")

    @Before
    fun setUp() {
        runBlockingTest {
            whenever(repository.getPlaylist()).thenReturn(flow {
                emit(expected)
            })
            viewmodel = PlayListViewModel(repository)
        }
    }

    @Test
    fun getDataFromRepository() = runBlockingTest{
        viewmodel.playlists.getValueForTest()
        verify(repository, times(1)).getPlaylist()
    }

    @Test
    fun emitPlaylistsFromRepository() = runBlockingTest{
        assertEquals(expected, viewmodel.playlists.getValueForTest())
    }
    @Test
    fun emitErrorWhenReceiveError(){
        runBlockingTest {
            whenever(repository.getPlaylist()).thenReturn(flow {
                emit(Result.failure<List<Playlist>>(exception))
            })
            viewmodel = PlayListViewModel(repository)
        }
        assertEquals(exception,viewmodel.playlists.getValueForTest()!!.exceptionOrNull())
    }


}