package com.techand.videoapp.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.techand.videoapp.R

class PlayListFragment : Fragment() {

    private lateinit var viewModel: PlayListViewModel
    private lateinit var viewModelFactory: PlayListViewModelFactory
    private val service =PlayListService(object : PlaylistApi{})
    private val repository = PlayListRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play_list, container, false)
        setViewModel()
        viewModel.playlists.observe(this as LifecycleOwner, { playlist ->
            if (playlist.getOrNull() != null)
                setRv(view, playlist.getOrNull()!!)
            else {
                TODO()
            }

        })
        return view
    }

    private fun setRv(
        view: View?,
        playlist: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = PlaylistAdapter(playlist)
        }
    }

    private fun setViewModel() {
        viewModelFactory = PlayListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayListViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlayListFragment().apply {}
    }
}
