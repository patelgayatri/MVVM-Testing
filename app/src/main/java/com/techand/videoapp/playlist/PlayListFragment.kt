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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_play_list.*
import kotlinx.android.synthetic.main.fragment_play_list.view.*
import javax.inject.Inject

@AndroidEntryPoint
class PlayListFragment : Fragment() {

    private lateinit var viewModel: PlayListViewModel

    @Inject
    lateinit var viewModelFactory: PlayListViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_play_list, container, false)
        setViewModel()

        viewModel.loader.observe(this as LifecycleOwner, { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE
            }
        })

        viewModel.playlists.observe(this as LifecycleOwner, { playlist ->
            if (playlist.getOrNull() != null)
                setRv(view.playlist_list, playlist.getOrNull()!!)
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
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlayListViewModel::class.java)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            PlayListFragment().apply {}
    }
}
