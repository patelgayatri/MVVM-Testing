package com.techand.videoapp.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PlayListServiceShould : BaseUnitTest() {
    private val api: PlaylistApi = mock()
    private val service = PlayListService(api)
    private val playlist: List<PlaylistRaw> = mock()

    @Test
    fun getServiceReturnPlaylist() = runBlockingTest {
        service.fetchPlayList().first()
        verify(api, times(1)).fetchPlayList()
    }

    @Test
    fun convertValuesToFlowResultAndEmitThem() = runBlockingTest {
        whenever(api.fetchPlayList()).thenReturn(playlist)
        assertEquals(playlist, service.fetchPlayList().first().getOrNull())
    }

    @Test
    fun emitErrorFromServiceWhenNetworkFail() = runBlockingTest {
        whenever(api.fetchPlayList()).thenThrow(RuntimeException("Error"))
        assertEquals("Error",service.fetchPlayList().first().exceptionOrNull()?.message)
    }

}