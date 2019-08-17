package com.igorwojda.showcase.feature.album.presentation.albumdetail

import androidx.lifecycle.viewModelScope
import com.igorwojda.showcase.base.presentation.viewmodel.BaseAction
import com.igorwojda.showcase.base.presentation.viewmodel.BaseViewModel
import com.igorwojda.showcase.base.presentation.viewmodel.BaseViewState
import com.igorwojda.showcase.feature.album.domain.model.AlbumDomainModel
import com.igorwojda.showcase.feature.album.domain.usecase.GetAlbumUseCase
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailViewModel.Action
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailViewModel.Action.AlbumLoadFailure
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailViewModel.Action.AlbumLoadSuccess
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailViewModel.ViewState
import kotlinx.coroutines.launch

internal class AlbumDetailViewModel(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val args: AlbumDetailFragmentArgs
) : BaseViewModel<ViewState, Action>() {

    init {
        getAlbum()
    }

    override val initialViewState = ViewState()

    private fun getAlbum() {
        viewModelScope.launch {
            getAlbumUseCase.execute(args.artistName, args.albumName, args.mbId).also {
                if (it != null) {
                    sendAction(AlbumLoadSuccess(it))
                } else {
                    sendAction(AlbumLoadFailure)
                }
            }
        }
    }

    override fun onReduceState(viewAction: Action) = when (viewAction) {
        is AlbumLoadSuccess -> viewState.copy(
            isLoading = false,
            isError = false,
            artist = viewAction.albumDomainModel.artist,
            name = viewAction.albumDomainModel.name,
            coverImageUrl = viewAction.albumDomainModel.getDefaultImageUrl() ?: ""
        )
        is AlbumLoadFailure -> viewState.copy(
            isLoading = false,
            isError = true,
            artist = "",
            name = "",
            coverImageUrl = ""
        )
    }

    data class ViewState(
        val isLoading: Boolean = true,
        val isError: Boolean = false,
        val name: String = "",
        val artist: String = "",
        val coverImageUrl: String = ""
    ) : BaseViewState

    internal sealed class Action : BaseAction {
        class AlbumLoadSuccess(val albumDomainModel: AlbumDomainModel) : Action()
        object AlbumLoadFailure : Action()
    }
}