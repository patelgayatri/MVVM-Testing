package com.techand.videoapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayListServiceShould : BaseUnitTest() {
    private val api: PlaylistApi = mock()
    private val service = PlayListService(api)
    val playlist: List<Playlist> = mock()

    @Test
    fun getServiceReturnPlaylist() = runBlockingTest{
        service.fetchPlayList()
        verify(api, times(1)).fetchPlayList()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem()= runBlockingTest {
        whenever(api.fetchPlayList()).thenReturn(playlist)
        assertEquals(playlist, service.fetchPlayList().first().getOrNull())
    }

}