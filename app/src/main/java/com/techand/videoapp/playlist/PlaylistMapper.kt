package com.techand.videoapp.playlist

import com.techand.videoapp.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {
    override fun invoke(p1: List<PlaylistRaw>): List<Playlist> {
        return p1.map {
            if (it.category.equals("rock"))
                Playlist(it.id, it.name, it.category, R.drawable.ic_launcher_background)
            else
                Playlist(it.id, it.name, it.category, R.mipmap.ic_launcher)
        }
    }

}
