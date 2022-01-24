package com.techand.videoapp.playlist

import com.techand.videoapp.R
import com.techand.videoapp.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1", "name", "category")
    private val playlistRockRaw = PlaylistRaw("1", "name", "rock")
    private val mapper = PlaylistMapper()
    private val playlists = mapper(listOf(playlistRaw))
    private val playlist = playlists[0]
    private val playlistRock = mapper(listOf(playlistRockRaw))[0]

    @Test
    fun keepSameId() {
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory() {
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock() {
        assertEquals(R.mipmap.ic_launcher, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory() {
        assertEquals(R.drawable.ic_launcher_background, playlistRock.image)
    }


}