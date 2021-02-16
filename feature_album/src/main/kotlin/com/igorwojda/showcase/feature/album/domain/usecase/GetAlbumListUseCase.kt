package com.igorwojda.showcase.feature.album.domain.usecase

import com.igorwojda.showcase.feature.album.domain.model.AlbumDomainModel
import com.igorwojda.showcase.feature.album.domain.repository.AlbumRepository
import java.io.IOException

internal class GetAlbumListUseCase(
    private val albumRepository: AlbumRepository
) {

    sealed class Result {
        data class Success(val data: List<AlbumDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(): Result {
        // Due to API limitations search with custom phrase have to be performed to get albums
        val phrase = "Jackson"

        return try {
            Result.Success(albumRepository.searchAlbum(phrase).filter { it.getDefaultImageUrl() != null })
        } catch (e: IOException) {
            Result.Error(e)
        }
    }
}
