package com.igorwojda.showcase.feature.album.presentation.albumlist

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.igorwojda.showcase.base.presentation.extension.observe
import com.igorwojda.showcase.base.presentation.fragment.BaseContainerFragment
import com.igorwojda.showcase.feature.album.R
import com.igorwojda.showcase.feature.album.presentation.albumlist.recyclerview.AlbumAdapter
import com.igorwojda.showcase.feature.album.presentation.albumlist.recyclerview.GridAutofitLayoutManager
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.kodein.di.generic.instance

class AlbumListFragment : BaseContainerFragment() {

    private val viewModel: AlbumListViewModel by instance()

    override val layoutResourceId = R.layout.fragment_album_list

    private val albumAdapter: AlbumAdapter by instance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = checkNotNull(context)

        albumAdapter.setOnDebouncedClickListener {
            val navDirections = AlbumListFragmentDirections.actionAlbumListToAlbumDetail(it.artist, it.name, it.mbId)
            findNavController().navigate(navDirections)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            val columnWidth = context.resources.getDimension(R.dimen.image_size).toInt()
            layoutManager =
                GridAutofitLayoutManager(
                    context,
                    columnWidth
                )
            adapter = albumAdapter
        }

        observe(viewModel.stateLiveData, ::onStateChange)
    }

    private fun onStateChange(viewState: AlbumListViewModel.ViewState) {
        albumAdapter.albums = viewState.albums
        progressBar.visible = viewState.isLoading
    }
}
