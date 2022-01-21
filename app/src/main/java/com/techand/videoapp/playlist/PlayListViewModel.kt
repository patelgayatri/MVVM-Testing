package com.techand.videoapp.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData

class PlayListViewModel(private val repository: PlayListRepository) : ViewModel() {

    val playlists = liveData<Result<List<Playlist>>>{
        emitSource(repository.getPlaylist().asLiveData())
    }


}
