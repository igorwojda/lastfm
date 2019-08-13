package com.igorwojda.showcase.feature.album.presentation.albumdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.igorwojda.showcase.base.presentation.extension.toLiveData
import com.igorwojda.showcase.base.presentation.viewmodel.BaseViewModel
import com.igorwojda.showcase.feature.album.domain.model.AlbumDomainModel
import com.igorwojda.showcase.feature.album.domain.usecase.GetAlbumUseCase
import kotlinx.coroutines.launch

internal class AlbumDetailViewModel(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val args: AlbumDetailFragmentArgs,
    private val imageLoader: ImageLoader
) : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState = _viewState.toLiveData()

    init {
        _viewState.value = ViewState()
    }

    private val currentViewState
        get() = viewState.value ?: throw RuntimeException("ViewModel state is empty")

    override fun onLoadData() {
        getAlbum()
    }

    private fun getAlbum() {
        viewModelScope.launch {
            getAlbumUseCase.execute(
                args.artistName,
                args.albumName,
                args.mbId
            ).also {
                if (it != null) {
                    sendAction(ViewAction.AlbumDataLoadSuccess(it))
                }
                // ToDo: support error
            }
        }
    }

    // sendAction method or rather one method per action name
    // eg.
    fun sendAction(viewAction: ViewAction) {
        _viewState.value = reducer(viewAction)
    }

    private fun reducer(viewAction: ViewAction): ViewState = when (viewAction) {
        is ViewAction.ImageLoadingSuccess -> {
            currentViewState.copy(
                isImageLoading = true
            )
        }
        is ViewAction.ImageLoadingError -> {
            currentViewState.copy(
                isImageLoading = true
            )
        }
        is ViewAction.AlbumDataLoadSuccess -> {
            val imageUrl = viewAction.albumDomainModel.images.first().url

//            val image
//
//            viewModelScope.launch {
//                image = imageLoader.get(imageUrl, {})
//            }


            currentViewState.copy(
                isImageLoading = true,
                artist = viewAction.albumDomainModel.artist,
                name = viewAction.albumDomainModel.name,
                imageUrl = ""
            )
        }
    }

    data class ViewState(
        val isImageLoading: Boolean = false,
        val isImageLoaded: Boolean = false,
        val isProgressBarVisible: Boolean = true,
        val name: String = "",
        val artist: String = "",
        val imageUrl: String = ""
    )

    sealed class ViewAction {
        object ImageLoadingSuccess : ViewAction()
        object ImageLoadingError : ViewAction()
        class AlbumDataLoadSuccess(val albumDomainModel: AlbumDomainModel) : ViewAction()
    }
}

//val url = viewState.images.firstOrNull { it.size == AlbumDomainImageSize.LARGE }?.url
//
//if (viewState.images.isNotEmpty() && !url.isNullOrEmpty()) {
//    loadImage(url)
//}


//interface ViewState
//
//interface ViewAction
//
//interface Reducer<S : ViewState, A : ViewAction> {
//    fun reduce(state: S, action: A)
//}


// initial viewState
// State.Empty
// TImetravel debugger
// Coroutines flow is  quite helpfull
//middleeware (secase, reepo?) emmits loading action (SearchLoadingAction, SearchSuccesAction)...hmm better foe few views using the same usecase?
