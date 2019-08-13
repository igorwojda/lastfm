package com.igorwojda.showcase.feature.album.presentation.albumdetails

import android.os.Bundle
import android.view.View
import coil.api.load
import com.igorwojda.showcase.base.presentation.extension.observe
import com.igorwojda.showcase.base.presentation.fragment.BaseContainerFragment
import com.igorwojda.showcase.feature.album.R
import com.pawegio.kandroid.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_album_detail.*
import org.kodein.di.generic.instance

internal class AlbumDetailFragment : BaseContainerFragment() {

    override val viewModel: AlbumDetailViewModel by instance()

    private val picasso: Picasso by instance()

    override val layoutResourceId = R.layout.fragment_album_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.viewState, ::onStateChange)
    }

    private fun onStateChange(viewState: AlbumDetailViewModel.ViewState) {
        progressBar.visible = viewState.isProgressBarVisible

        nameTextView.text = viewState.name
        nameTextView.visible = viewState.name.isNotBlank()

        artistTextView.text = viewState.artist
        artistTextView.visible = viewState.artist.isNotBlank()

//        coverImageView.setImageDrawable(viewState.coverImage)

        val imageSize = 800

        coverImageView.load(viewState.coverImage2) {
            size(2000, 2000)
        }
    }
}


