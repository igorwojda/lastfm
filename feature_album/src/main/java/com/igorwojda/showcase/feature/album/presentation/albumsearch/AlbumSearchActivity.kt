package com.igorwojda.showcase.feature.album.presentation.albumsearch

import android.content.Context
import android.os.Bundle
import com.igorwojda.showcase.base.presentation.activity.BaseContainerActivity
import com.igorwojda.showcase.base.presentation.extension.startActivity
import com.igorwojda.showcase.feature.album.R

class AlbumSearchActivity : BaseContainerActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity<AlbumSearchActivity>()
        }
    }

    override val layoutResourceId = R.layout.activity_album_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayFragment { AlbumSearchFragment() }

        title = resources.getString(R.string.search_album)
    }
}
